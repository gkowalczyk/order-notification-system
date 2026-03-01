package com.example.ordernotificationsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_event_audit")
@Setter
public class OrderEventAudit {

    @Id
    private UUID eventId;
    private String shipmentNumber;
    private String recipientEmail;
    private String recipientCountry;
    private String senderCountry;
    private int statusCode;
    private LocalDateTime receivedAt;
}
