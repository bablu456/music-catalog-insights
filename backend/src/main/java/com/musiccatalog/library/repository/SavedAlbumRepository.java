package com.musiccatalog.library.repository;

import com.musiccatalog.library.entity.SavedAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SavedAlbumRepository extends JpaRepository<SavedAlbum, UUID> {
    List<SavedAlbum> findAllByUserId(UUID userId);
    Optional<SavedAlbum> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByAppleCatalogIdAndUserId(String appleCatalogId, UUID userId);
    
    // For Activity Timeline and Analytics
    org.springframework.data.domain.Page<SavedAlbum> findAllByUserIdOrderByCreatedAtDesc(UUID userId, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<SavedAlbum> findAllByUserIdOrderByUpdatedAtDesc(UUID userId, org.springframework.data.domain.Pageable pageable);
    
    long countByUserId(UUID userId);
    
    long countByUserIdAndCreatedAtAfter(UUID userId, java.time.LocalDateTime date);

    @org.springframework.data.jpa.repository.Query("SELECT a.genre, COUNT(a) FROM SavedAlbum a WHERE a.user.id = :userId AND a.genre IS NOT NULL GROUP BY a.genre ORDER BY COUNT(a) DESC")
    List<Object[]> findTopGenresByUserId(@org.springframework.data.repository.query.Param("userId") UUID userId, org.springframework.data.domain.Pageable pageable);

    @org.springframework.data.jpa.repository.Query("SELECT a.artistName, COUNT(a) FROM SavedAlbum a WHERE a.user.id = :userId AND a.artistName IS NOT NULL GROUP BY a.artistName ORDER BY COUNT(a) DESC")
    List<Object[]> findTopArtistsByUserId(@org.springframework.data.repository.query.Param("userId") UUID userId, org.springframework.data.domain.Pageable pageable);

    @org.springframework.data.jpa.repository.Query("SELECT SUBSTRING(a.releaseDate, 1, 4), COUNT(a) FROM SavedAlbum a WHERE a.user.id = :userId AND a.releaseDate IS NOT NULL GROUP BY SUBSTRING(a.releaseDate, 1, 4) ORDER BY SUBSTRING(a.releaseDate, 1, 4)")
    List<Object[]> findAlbumsByReleaseYear(@org.springframework.data.repository.query.Param("userId") UUID userId);

    @org.springframework.data.jpa.repository.Query("SELECT CAST(a.userRating AS string), COUNT(a) FROM SavedAlbum a WHERE a.user.id = :userId AND a.userRating IS NOT NULL GROUP BY a.userRating ORDER BY a.userRating")
    List<Object[]> findRatingDistribution(@org.springframework.data.repository.query.Param("userId") UUID userId);

    @org.springframework.data.jpa.repository.Query("SELECT AVG(a.userRating) FROM SavedAlbum a WHERE a.user.id = :userId AND a.userRating IS NOT NULL")
    Double getAverageRatingByUserId(@org.springframework.data.repository.query.Param("userId") UUID userId);
}
