package com.musiccatalog.library.entity;

import com.musiccatalog.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "saved_albums", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "appleCatalogId"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String appleCatalogId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artistName;

    private String genre;

    private String releaseDate;

    private Integer trackCount;

    @Column(length = 1000)
    private String artworkUrl;

    private Integer userRating;

    @Column(length = 2000)
    private String userNotes;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
