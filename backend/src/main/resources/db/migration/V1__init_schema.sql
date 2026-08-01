-- V1__init_schema.sql

CREATE TABLE users (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE saved_albums (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    apple_catalog_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    artist_name VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    release_date VARCHAR(255),
    track_count INTEGER,
    artwork_url VARCHAR(1000),
    user_rating INTEGER,
    user_notes VARCHAR(2000),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_saved_albums PRIMARY KEY (id),
    CONSTRAINT fk_saved_albums_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT uk_saved_albums_user_apple_id UNIQUE (user_id, apple_catalog_id)
);

CREATE TABLE search_history (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    query VARCHAR(255) NOT NULL,
    searched_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_search_history PRIMARY KEY (id),
    CONSTRAINT fk_search_history_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Performance Indexes

-- For filtering and analytics
CREATE INDEX idx_saved_albums_user_genre ON saved_albums (user_id, genre);
CREATE INDEX idx_saved_albums_user_artist ON saved_albums (user_id, artist_name);
CREATE INDEX idx_saved_albums_user_release_date ON saved_albums (user_id, release_date);

-- For sorting and timeline queries
CREATE INDEX idx_saved_albums_user_created_at ON saved_albums (user_id, created_at DESC);
CREATE INDEX idx_saved_albums_user_updated_at ON saved_albums (user_id, updated_at DESC);

-- For search history timeline
CREATE INDEX idx_search_history_user_searched_at ON search_history (user_id, searched_at DESC);
