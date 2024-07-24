package org.launchcode.threemix.repository;

import org.launchcode.threemix.model.BlockedSong;
import org.launchcode.threemix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockedSongRepository extends JpaRepository<BlockedSong, Long> {
    List<BlockedSong> findByUser(User user);
}
