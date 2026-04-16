package com.example.ordernotificationsystem.config;

import com.example.ordernotificationsystem.dto.OrderEventMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderEventMessage> kafkaListenerContainerFactory(
            ConsumerFactory<String, OrderEventMessage> consumerFactory,
            @Value("${audit.consumer.concurrency:3}") int concurrency
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderEventMessage>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}


