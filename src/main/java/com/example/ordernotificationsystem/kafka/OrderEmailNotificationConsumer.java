package com.example.ordernotificationsystem.kafka;

import com.example.ordernotificationsystem.dto.OrderEventMessage;
import com.example.ordernotificationsystem.service.EmailNotificationService;
import com.example.ordernotificationsystem.service.MockEmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEmailNotificationConsumer {

    private final EmailNotificationService builder;
    private final MockEmailSender sender;

    @KafkaListener(
            topics = "${app.kafka.topic.order-events:order.events}",
            groupId = "email-sender",
            containerFactory = "emailKafkaListenerContainerFactory"
    )
    public void consume(OrderEventMessage message, Acknowledgment ack) {
        try {
            String subject = builder.buildEmailSubject(message);
            String body = builder.buildEmailBody(message);
            sender.send(message.getRecipientEmail(), subject, body);
            ack.acknowledge();
        } catch (Exception ex) {
            log.error("EMAIL MOCK failed for eventId={}", message.getEventId(), ex);
            throw ex;
        }
    }
}
