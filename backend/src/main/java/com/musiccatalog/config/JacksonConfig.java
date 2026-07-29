package com.musiccatalog.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Customizes the Jackson ObjectMapper used by Spring Boot.
 * Modifying it via Jackson2ObjectMapperBuilderCustomizer ensures we do not 
 * overwrite Spring Boot's auto-configured ObjectMapper defaults.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder
                // Enforces ISO-8601 formatting for Java 8 Dates instead of timestamps
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
