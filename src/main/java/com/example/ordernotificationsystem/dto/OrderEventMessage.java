package com.example.ordernotificationsystem.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.Instant;
import java.util.UUID;
@RequiredArgsConstructor
@Getter
public class OrderEventMessage {

    private final UUID eventId;
    private final String shipmentNumber;
    private final String recipientEmail;
    private final String recipientCountry;
    private final String senderCountry;
    private final int statusCode;
    private final Instant receivedAt;
}
