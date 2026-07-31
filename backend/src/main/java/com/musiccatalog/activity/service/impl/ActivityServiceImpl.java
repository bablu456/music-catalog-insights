package com.musiccatalog.activity.service.impl;

import com.musiccatalog.activity.dto.TimelineEventDTO;
import com.musiccatalog.activity.entity.SearchHistory;
import com.musiccatalog.activity.repository.SearchHistoryRepository;
import com.musiccatalog.activity.service.ActivityService;
import com.musiccatalog.library.entity.SavedAlbum;
import com.musiccatalog.library.repository.SavedAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final SavedAlbumRepository savedAlbumRepository;

    @Override
    public List<TimelineEventDTO> getRecentActivity(UUID userId) {
        List<TimelineEventDTO> events = new ArrayList<>();

        // Fetch recent searches
        List<SearchHistory> searches = searchHistoryRepository.findByUserIdOrderBySearchedAtDesc(userId, PageRequest.of(0, 10));
        for (SearchHistory s : searches) {
            events.add(TimelineEventDTO.builder()
                    .id(s.getId().toString())
                    .type("SEARCH")
                    .title("Searched for music")
                    .description("You searched for: " + s.getQuery())
                    .metadata(s.getQuery())
                    .timestamp(s.getSearchedAt())
                    .build());
        }

        // Fetch recent albums
        List<SavedAlbum> albums = savedAlbumRepository.findAllByUserIdOrderByUpdatedAtDesc(userId, PageRequest.of(0, 15)).getContent();
        for (SavedAlbum a : albums) {
            boolean isNew = a.getCreatedAt().equals(a.getUpdatedAt());
            
            if (isNew) {
                events.add(TimelineEventDTO.builder()
                        .id(a.getId().toString() + "-save")
                        .type("SAVE")
                        .title("Saved Album")
                        .description("Saved '" + a.getTitle() + "' by " + a.getArtistName())
                        .metadata(a.getAppleCatalogId())
                        .timestamp(a.getCreatedAt())
                        .build());
            } else {
                // Determine if it was a rating or note update by checking if they are not null
                // To keep it simple, just call it an update
                String desc = "Updated details for '" + a.getTitle() + "'";
                if (a.getUserRating() != null && a.getUserRating() > 0) {
                    desc = "Rated '" + a.getTitle() + "' " + a.getUserRating() + " stars";
                }
                
                events.add(TimelineEventDTO.builder()
                        .id(a.getId().toString() + "-update")
                        .type("UPDATE")
                        .title("Updated Library")
                        .description(desc)
                        .metadata(a.getAppleCatalogId())
                        .timestamp(a.getUpdatedAt())
                        .build());
            }
        }

        // Sort combined list descending by timestamp
        return events.stream()
                .sorted(Comparator.comparing(TimelineEventDTO::getTimestamp).reversed())
                .limit(20)
                .collect(Collectors.toList());
    }
}
