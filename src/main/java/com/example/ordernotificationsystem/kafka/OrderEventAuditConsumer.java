package com.example.ordernotificationsystem.kafka;

import com.example.ordernotificationsystem.dto.OrderEventMessage;
import com.example.ordernotificationsystem.entity.OrderEventAudit;
import com.example.ordernotificationsystem.entity.OrderEventAuditRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class OrderEventAuditConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventAuditConsumer.class);
    private final OrderEventAuditRepository orderEventAuditRepository;

    @KafkaListener(
            topics = "${app.kafka.topic.order-events:order.events}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderEventMessage message, Acknowledgment ack) {

        try {
            OrderEventAudit orderEventAudit = new OrderEventAudit();
            orderEventAudit.setEventId(message.getEventId());
            orderEventAudit.setShipmentNumber(message.getShipmentNumber());
            orderEventAudit.setRecipientEmail(message.getRecipientEmail());
            orderEventAudit.setRecipientCountry(message.getRecipientCountry());
            orderEventAudit.setSenderCountry(message.getSenderCountry());
            orderEventAudit.setStatusCode(message.getStatusCode());
            orderEventAudit.setReceivedAt(LocalDateTime.ofInstant(message.getReceivedAt(), ZoneOffset.UTC));
            orderEventAuditRepository.save(orderEventAudit);
            ack.acknowledge();
            log.info("Audit saved eventId={} into database", message.getEventId());
        } catch (Exception e) {
            log.error("Failed to save audit for eventId={}. Error: {}", message.getEventId(), e.getMessage());
        }
    }
}


