package com.example.ordernotificationsystem.kafka;

import com.example.ordernotificationsystem.dto.OrderEventMessage;
import com.example.ordernotificationsystem.dto.OrderEventRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderEventMessage> kafkaTemplate;
    private String topic;

    public OrderEventProducer(KafkaTemplate<String, OrderEventMessage> kafkaTemplate,
                              @Value("${app.kafka.topic.order-events:order.events}")String topic) {
       this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void publishOrderEvent(UUID eventID, OrderEventRequest orderEventRequest) {
        OrderEventMessage messageWithID = new OrderEventMessage(
                eventID,
                orderEventRequest.getShipmentNumber(),
                orderEventRequest.getRecipientEmail(),
                orderEventRequest.getRecipientCountry(),
                orderEventRequest.getSenderCountry(),
                orderEventRequest.getStatusCode(),
                Instant.now()
        );
        kafkaTemplate.send(topic, orderEventRequest.getShipmentNumber(), messageWithID);
    }
}
