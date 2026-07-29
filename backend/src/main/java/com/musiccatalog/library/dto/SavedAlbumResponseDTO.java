package com.musiccatalog.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedAlbumResponseDTO {
    private UUID id;
    private String appleCatalogId;
    private String title;
    private String artistName;
    private String genre;
    private String releaseDate;
    private Integer trackCount;
    private String artworkUrl;
    private Integer userRating;
    private String userNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
