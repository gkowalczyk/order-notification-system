package com.example.ordernotificationsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MockEmailSender {

    private static final Logger log = LoggerFactory.getLogger(MockEmailSender.class);

    public void send(String toEmail, String subject, String body) {
        log.info("MOCK EMAIL -> to={}, subject={}\n{}", toEmail, subject, body);
    }
}
