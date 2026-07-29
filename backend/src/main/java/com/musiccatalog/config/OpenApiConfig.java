package com.musiccatalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the OpenAPI/Swagger documentation metadata.
 * Provides a standardized way for frontend developers to explore the API structure.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Music Catalog AI Platform")
                        .version("1.0.0")
                        .description("Backend API for managing music metadata and AI insights"));
    }
}
