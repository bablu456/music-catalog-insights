package com.musiccatalog.activity.service;

import com.musiccatalog.auth.entity.User;
import java.util.UUID;

public interface SearchHistoryService {
    void saveSearchQuery(UUID userId, String query);
}
