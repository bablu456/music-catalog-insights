package com.musiccatalog.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimelineEventDTO {
    private String id;
    private String type; // "SEARCH", "SAVE", "RATING", "NOTE"
    private String title;
    private String description;
    private String metadata; // e.g. query string for SEARCH, or catalog id for SAVED_ALBUM
    private LocalDateTime timestamp;
}
