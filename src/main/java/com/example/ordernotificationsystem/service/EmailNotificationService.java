package com.example.ordernotificationsystem.service;

import com.example.ordernotificationsystem.dto.OrderEventMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    public String buildEmailBody(OrderEventMessage msg) {
        return """
                Order notification for shipment
                                            eventId: %s
                                            shipmentNumber: %s
                                            recipientEmail: %s
                                            recipientCountry: %s
                                            senderCountry: %s
                                            statusCode: %d
                                            receivedAt: %s
                """.formatted(
                msg.getEventId(),
                msg.getShipmentNumber(),
                msg.getRecipientEmail(),
                msg.getRecipientCountry(),
                msg.getSenderCountry(),
                msg.getStatusCode(),
                msg.getReceivedAt()
        );
    }

    public String buildEmailSubject(OrderEventMessage msg) {
        return "Order Notification - Shipment %s - Status %d".formatted(
                msg.getShipmentNumber(),
                msg.getStatusCode()
        );
    }
}
