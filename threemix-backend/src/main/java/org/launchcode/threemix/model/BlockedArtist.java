package org.launchcode.threemix.model;

import jakarta.persistence.*;

@Entity
public class BlockedArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name; // Assuming this is the field holding the artist's name

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

