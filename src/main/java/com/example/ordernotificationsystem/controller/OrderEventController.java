package com.example.ordernotificationsystem.controller;

import com.example.ordernotificationsystem.dto.OrderEventRequest;
import com.example.ordernotificationsystem.dto.OrderEventResponse;
import com.example.ordernotificationsystem.kafka.OrderEventProducer;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
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
    private final RateLimiter ingestRateLimiter;

    @PostMapping("/order-events")
    public ResponseEntity<OrderEventResponse> sendOrderEvent(
            @Valid @RequestBody OrderEventRequest request) {
        try {
            UUID eventId = UUID.randomUUID();

            Runnable task = RateLimiter.decorateRunnable(
                    ingestRateLimiter,
                    () -> producer.publishOrderEvent(eventId, request)
            );
            task.run();

            return ResponseEntity.accepted()
                    .body(new OrderEventResponse(eventId));
        } catch (RequestNotPermitted ex) {
            return ResponseEntity.status(429).build();
        }
    }
    }