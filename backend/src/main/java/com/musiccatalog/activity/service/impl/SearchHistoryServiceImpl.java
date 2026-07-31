package com.musiccatalog.activity.service.impl;

import com.musiccatalog.activity.entity.SearchHistory;
import com.musiccatalog.activity.repository.SearchHistoryRepository;
import com.musiccatalog.activity.service.SearchHistoryService;
import com.musiccatalog.auth.entity.User;
import com.musiccatalog.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SearchHistoryServiceImpl implements SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final UserRepository userRepository;

    @Async
    @Override
    @Transactional
    public void saveSearchQuery(UUID userId, String query) {
        if (query == null || query.trim().isEmpty()) {
            return;
        }

        userRepository.findById(userId).ifPresent(user -> {
            SearchHistory history = SearchHistory.builder()
                    .user(user)
                    .query(query.trim())
                    .build();
            searchHistoryRepository.save(history);
        });
    }
}
