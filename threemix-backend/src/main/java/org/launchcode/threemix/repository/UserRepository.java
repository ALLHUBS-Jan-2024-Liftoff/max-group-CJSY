package org.launchcode.threemix.repository;

import org.launchcode.threemix.model.BlockedArtist;
import org.launchcode.threemix.model.BlockedSong;
import org.launchcode.threemix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

