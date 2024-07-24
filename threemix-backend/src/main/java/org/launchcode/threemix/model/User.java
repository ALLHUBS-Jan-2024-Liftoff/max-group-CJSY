package org.launchcode.threemix.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "user")
    private List<BlockedArtist> blockedArtists;

    @OneToMany(mappedBy = "user")
    private List<BlockedSong> blockedSongs;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BlockedArtist> getBlockedArtists() {
        return blockedArtists;
    }

    public void setBlockedArtists(List<BlockedArtist> blockedArtists) {
        this.blockedArtists = blockedArtists;
    }

    public List<BlockedSong> getBlockedSongs() {
        return blockedSongs;
    }

    public void setBlockedSongs(List<BlockedSong> blockedSongs) {
        this.blockedSongs = blockedSongs;
    }
}