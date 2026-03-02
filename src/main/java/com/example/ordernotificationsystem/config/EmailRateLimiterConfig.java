package com.example.ordernotificationsystem.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class EmailRateLimiterConfig {

    @Bean
    public RateLimiter emailRateLimiter(
            @Value("${email.rate.limit.perSecond:20}") int perSecond,
            @Value("${email.rate.limit.timeout:5s}") Duration timeout
    ) {
        var config = RateLimiterConfig.custom()
                .limitForPeriod(perSecond)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(timeout)
                .build();

        return RateLimiter.of("emailRateLimiter", config);
    }
}
