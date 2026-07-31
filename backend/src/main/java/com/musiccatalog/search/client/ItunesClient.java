package com.musiccatalog.search.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musiccatalog.exception.BusinessException;
import com.musiccatalog.search.dto.ItunesResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Component
public class ItunesClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ItunesClient(WebClient.Builder builder, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.webClient = builder.build();
    }

    public ItunesResponseDTO search(String query) {
        long startTime = System.currentTimeMillis();
        
        log.info("Outgoing Request to iTunes API for query: {}", query);
        String rawResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("itunes.apple.com")
                        .path("/search")
                        .queryParam("term", query)
                        .queryParam("entity", "album")
                        .queryParam("limit", 20)
                        .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> {
                            log.error("iTunes API responded with status: {}", response.statusCode());
                            return Mono.error(new BusinessException("Error communicating with iTunes API", "EXTERNAL_API_ERROR"));
                        })
                .bodyToMono(String.class)
                .doOnSuccess(res -> log.info("iTunes API request successful. Response time: {} ms", (System.currentTimeMillis() - startTime)))
                .retryWhen(Retry.backoff(2, Duration.ofMillis(500))
                        .filter(this::isTransientError)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> 
                                new BusinessException("iTunes API request failed after retries", "EXTERNAL_API_ERROR")))
                .onErrorMap(e -> {
                    if (!(e instanceof BusinessException)) {
                        log.error("Unexpected error during iTunes API call: {}", e.getMessage());
                        return new BusinessException("Unexpected error communicating with iTunes API", "EXTERNAL_API_ERROR");
                    }
                    return e;
                })
                .block();

        try {
            return objectMapper.readValue(rawResponse, ItunesResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse iTunes API response: {}", e.getMessage());
            throw new BusinessException("Failed to parse iTunes API response", "EXTERNAL_API_ERROR");
        }
    }

    private boolean isTransientError(Throwable throwable) {
        if (throwable instanceof java.util.concurrent.TimeoutException) {
            return true;
        }
        if (throwable instanceof WebClientResponseException) {
            WebClientResponseException ex = (WebClientResponseException) throwable;
            return ex.getStatusCode().is5xxServerError() || ex.getStatusCode().value() == 429;
        }
        if (throwable instanceof java.net.ConnectException) {
            return true;
        }
        return false;
    }
}
