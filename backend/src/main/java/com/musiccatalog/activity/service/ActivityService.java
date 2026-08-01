package com.musiccatalog.activity.service;

import com.musiccatalog.activity.dto.TimelineEventDTO;
import java.util.List;
import java.util.UUID;

import com.musiccatalog.common.PagedResponseDTO;

public interface ActivityService {
    PagedResponseDTO<TimelineEventDTO> getRecentActivity(UUID userId, int page, int size);
}
