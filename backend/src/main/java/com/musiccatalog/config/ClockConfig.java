package com.musiccatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Provides a global Clock bean for date and time generation.
 * This is crucial for testability, allowing tests to mock the system time.
 */
@Configuration
public class ClockConfig {

    @Bean
    public Clock clock() {
        // Enforce UTC system-wide for consistent timestamps
        return Clock.systemUTC();
    }
}
