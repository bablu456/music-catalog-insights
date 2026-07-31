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
    org.springframework.data.domain.Page<SavedAlbum> findAllByUserIdOrderByUpdatedAtDesc(UUID userId, org.springframework.data.domain.Pageable pageable);
    List<SavedAlbum> findAllByUserIdAndCreatedAtAfter(UUID userId, java.time.LocalDateTime date);
}
