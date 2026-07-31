package com.musiccatalog.ai.provider.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musiccatalog.ai.dto.RecommendationResponseDTO;
import com.musiccatalog.ai.provider.AIProvider;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiAIProviderImpl implements AIProvider {

    private static final Logger log = LoggerFactory.getLogger(GeminiAIProviderImpl.class);

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    @Value("${gemini.model}")
    private String geminiModel;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        log.info("Loaded Gemini Model: {}", geminiModel);
    }

    @Override
    public RecommendationResponseDTO generateInsights(String prompt) {
        if (geminiApiKey == null || geminiApiKey.trim().isEmpty()) {
            return getFallbackResponse("API Key missing. Cannot generate AI recommendations.");
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> textPart = new HashMap<>();
            textPart.put("text", prompt);

            Map<String, Object> partsMap = new HashMap<>();
            partsMap.put("parts", List.of(textPart));

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", List.of(partsMap));

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            String url = "https://generativelanguage.googleapis.com/v1beta/models/" + geminiModel + ":generateContent?key=" + geminiApiKey;
            String responseStr = restTemplate.postForObject(url, requestEntity, String.class);

            if (responseStr == null) {
                return getFallbackResponse("No response from Gemini API.");
            }

            JsonNode rootNode = objectMapper.readTree(responseStr);
            JsonNode candidates = rootNode.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode parts = candidates.get(0).path("content").path("parts");
                if (parts.isArray() && parts.size() > 0) {
                    String generatedText = parts.get(0).path("text").asText();
                    return parseJsonResponse(generatedText);
                }
            }

            return getFallbackResponse("Failed to parse AI response structure.");
            
        } catch (RestClientResponseException e) {
            log.error("Gemini API Error! Status: {}, Message: {}, Response Body: {}", 
                      e.getStatusCode(), e.getMessage(), e.getResponseBodyAsString());
            return getFallbackResponse("AI Service encountered an error (HTTP " + e.getStatusCode() + ").");
        } catch (Exception e) {
            log.error("Unexpected Error calling Gemini API: {}", e.getMessage(), e);
            return getFallbackResponse("AI Service unavailable: " + e.getMessage());
        }
    }

    private RecommendationResponseDTO parseJsonResponse(String generatedText) {
        try {
            String jsonStr = generatedText.trim();
            if (jsonStr.startsWith("```json")) {
                jsonStr = jsonStr.substring(7);
            }
            if (jsonStr.startsWith("```")) {
                jsonStr = jsonStr.substring(3);
            }
            if (jsonStr.endsWith("```")) {
                jsonStr = jsonStr.substring(0, jsonStr.length() - 3);
            }
            
            return objectMapper.readValue(jsonStr.trim(), RecommendationResponseDTO.class);
        } catch (Exception e) {
            return getFallbackResponse("Failed to map JSON response to DTO.");
        }
    }

    private RecommendationResponseDTO getFallbackResponse(String errorDetails) {
        return RecommendationResponseDTO.builder()
                .genreSummary("Could not generate insights at this time.")
                .favouriteArtist("Unavailable")
                .listeningTrends("Unavailable")
                .interestingObservations(errorDetails)
                .albumRecommendations(Collections.emptyList())
                .build();
    }
}
