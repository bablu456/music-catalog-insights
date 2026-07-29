package com.musiccatalog.search.client;

import com.musiccatalog.exception.BusinessException;
import com.musiccatalog.search.dto.ItunesResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ItunesClient {

    private final WebClient webClient;
    private static final String ITUNES_API_URL = "https://itunes.apple.com/search";

    public ItunesResponseDTO search(String query, String type) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("itunes.apple.com")
                        .path("/search")
                        .queryParam("term", query)
                        .queryParamIfPresent("entity", java.util.Optional.ofNullable(type))
                        .queryParam("limit", 25)
                        .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new BusinessException("Error communicating with iTunes API", "EXTERNAL_API_ERROR")))
                .bodyToMono(ItunesResponseDTO.class)
                .block(); // Block since our current architecture is standard Spring MVC
    }
}
