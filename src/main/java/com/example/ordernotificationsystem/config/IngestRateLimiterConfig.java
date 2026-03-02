package com.example.ordernotificationsystem.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class IngestRateLimiterConfig {

    @Bean
    public RateLimiter ingestRateLimiter(
            @Value("${ingest.rate.limit.perSecond:100}") int perSecond,
            @Value("${ingest.rate.limit.timeout:0ms}") Duration timeout
    ) {
        var config = RateLimiterConfig.custom()
                .limitForPeriod(perSecond)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(timeout)
                .build();

        return RateLimiter.of("ingestLimiter", config);
    }
}