package com.example.ordernotificationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class MockEmailSender {

    public void send(String toEmail, String subject, String body) {
        log.info("MOCK EMAIL -> to={}, subject={}\n{}", toEmail, subject, body);
    }
}
