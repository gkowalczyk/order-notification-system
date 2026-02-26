package com.example.ordernotificationsystem.dto;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderEventRequest {

    @NotBlank
    private final String shipmentNumber;

    @Email
    @NotBlank
    private String recipientEmail;

    @Size(min = 2, max = 2)
    @NotBlank
    private String recipientCountry;

    @Size(min = 2, max = 2)
    @NotBlank
    private String senderCountry;

    @Min(0)
    @Max(100)
    private int statusCode;
}
