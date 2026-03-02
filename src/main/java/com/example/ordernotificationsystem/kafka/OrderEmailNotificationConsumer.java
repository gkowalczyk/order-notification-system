package com.example.ordernotificationsystem.kafka;

import com.example.ordernotificationsystem.dto.OrderEventMessage;
import com.example.ordernotificationsystem.service.EmailNotificationService;
import com.example.ordernotificationsystem.service.MockEmailSender;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEmailNotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEmailNotificationConsumer.class);
    private final EmailNotificationService builder;
    private final MockEmailSender sender;
    private final RateLimiter emailRateLimiter;

    @KafkaListener(
            topics = "${app.kafka.topic.order-events:order.events}",
            groupId = "email-sender",
            containerFactory = "emailKafkaListenerContainerFactory"
    )
    public void consume(OrderEventMessage message, Acknowledgment ack) {
        try {
            String subject = builder.buildEmailSubject(message);
            String body = builder.buildEmailBody(message);

            Runnable sendEmailTask = RateLimiter.decorateRunnable(
                    emailRateLimiter,
                    () -> sender.send(message.getRecipientEmail(), subject, body));
            sendEmailTask.run();
            ack.acknowledge();
        } catch (RequestNotPermitted ex) {
            log.warn("EMAIL throttled (no permit). eventId={}", message.getEventId());
            throw ex;
        } catch (Exception ex) {
            log.error("EMAIL MOCK failed for eventId={}", message.getEventId(), ex);
            throw ex;
        }
    }
}
