package com.example.ordernotificationsystem.controller;

import com.example.ordernotificationsystem.dto.OrderEventRequest;
import com.example.ordernotificationsystem.dto.OrderEventResponse;
import com.example.ordernotificationsystem.kafka.OrderEventProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderEventController {

    private final OrderEventProducer producer;

    @PostMapping("/order-events")
    public ResponseEntity<OrderEventResponse> sendOrderEvent(
            @Valid @RequestBody OrderEventRequest request) {

        UUID eventId = UUID.randomUUID();
        producer.publishOrderEvent(eventId, request);

        return ResponseEntity.accepted()
                .body(new OrderEventResponse(eventId));
    }
}