-- Run this script as the threemix user to set up the threemix schema.

CREATE TABLE users (
    user_id VARCHAR(256) PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(256) NOT NULL,
    playlist_name VARCHAR(256) NOT NULL,
    stats_id INT NOT NULL,
    exported_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (stats_id) REFERENCES genre_stats (id)
        ON DELETE CASCADE
);

CREATE TABLE blocked_entities (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(256) NOT NULL,
    stats_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (stats_id) REFERENCES block_stats (id)
        ON DELETE CASCADE
);

CREATE TABLE genre_stats (
    id INT PRIMARY KEY AUTO_INCREMENT,
    genre1 VARCHAR(64) NOT NULL,
    genre2 VARCHAR(64) NOT NULL,
    genre3 VARCHAR(64) NOT NULL
);

CREATE TABLE block_stats (
    id INT PRIMARY KEY AUTO_INCREMENT,
    spotify_id VARCHAR(256) NOT NULL,
    entity_type VARCHAR(16) NOT NULL  -- 'TRACK' or 'ARTIST'
);
