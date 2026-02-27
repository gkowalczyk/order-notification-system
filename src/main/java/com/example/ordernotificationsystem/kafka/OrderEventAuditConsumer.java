package com.example.ordernotificationsystem.kafka;

import com.example.ordernotificationsystem.dto.OrderEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class OrderEventAuditConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventAuditConsumer.class);

    @KafkaListener(
            topics = "${app.kafka.topic.order-events:order.events}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(OrderEventMessage message, Acknowledgment ack) {
        log.info("Consumer received message eventId={}, shipment={}", message.getEventId(),
                message.getShipmentNumber());
        ack.acknowledge();
    }
}


