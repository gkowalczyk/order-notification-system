package com.example.ordernotificationsystem.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class OrderEventResponse {

    private final UUID eventId;
}
