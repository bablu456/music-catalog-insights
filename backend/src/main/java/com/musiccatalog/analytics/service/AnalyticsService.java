package com.musiccatalog.analytics.service;

import com.musiccatalog.analytics.dto.AnalyticsResponseDTO;

import java.util.UUID;

public interface AnalyticsService {
    AnalyticsResponseDTO getAnalytics(UUID userId);
}
