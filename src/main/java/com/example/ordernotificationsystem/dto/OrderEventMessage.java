package com.example.ordernotificationsystem.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventMessage {

    private UUID eventId;
    private String shipmentNumber;
    private String recipientEmail;
    private String recipientCountry;
    private String senderCountry;
    private int statusCode;
    private Instant receivedAt;
}
