package com.musiccatalog.activity.service;

import com.musiccatalog.activity.dto.TimelineEventDTO;
import java.util.List;
import java.util.UUID;

public interface ActivityService {
    List<TimelineEventDTO> getRecentActivity(UUID userId);
}
