package org.launchcode.threemix.service;

import org.launchcode.threemix.model.BlockedArtist;
import org.launchcode.threemix.model.BlockedSong;
import org.launchcode.threemix.model.User;
import org.launchcode.threemix.repository.BlockedArtistRepository;
import org.launchcode.threemix.repository.BlockedSongRepository;
import org.launchcode.threemix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlockedArtistRepository blockedArtistRepository;

    @Autowired
    private BlockedSongRepository blockedSongRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<BlockedArtist> getBlockedArtists(User user) {
        return blockedArtistRepository.findByUser(user);
    }

    public List<BlockedSong> getBlockedSongs(User user) {
        return blockedSongRepository.findByUser(user);
    }
}