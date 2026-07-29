package com.musiccatalog.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedAlbumRequestDTO {

    @NotBlank(message = "Apple Catalog ID is required")
    private String appleCatalogId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Artist name is required")
    private String artistName;

    private String genre;

    private String releaseDate;

    @Min(value = 1, message = "Track count must be at least 1")
    private Integer trackCount;

    private String artworkUrl;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer userRating;

    private String userNotes;
}
