package com.example.ordernotificationsystem.config;

import com.example.ordernotificationsystem.dto.OrderEventMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;


@Configuration
public class EmailKafkaListenerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderEventMessage> emailKafkaListenerContainerFactory(
            ConsumerFactory<String, OrderEventMessage> consumerFactory,
            @Value("${email.consumer.concurrency:1}") int concurrency) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderEventMessage>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}