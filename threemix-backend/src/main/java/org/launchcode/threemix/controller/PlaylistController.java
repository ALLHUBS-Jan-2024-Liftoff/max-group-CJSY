package org.launchcode.threemix.controller;

import org.launchcode.threemix.domain.User;
import org.launchcode.threemix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PlaylistController {

    @Autowired
    public UserService userService;

    @PostMapping
    public Map<String, Object> createPlaylist(@CookieValue("accessToken") String accessToken,
                                              @RequestParam List<String> chosenGenres) {

        String userId = "accessToken.getSubject()";

        User user = userService.getUserHistory(userId);

        Map<String, List<Object>> blockedList = new HashMap<>();
        blockedList.put("tracts", Collections.singletonList(user.getBlockedTracks()));
        blockedList.put("artists", Collections.singletonList(user.getBlockedArtists()));

        Map<String, Object> trackRecommendations = Map.of();

        filterRecommendations(trackRecommendations, blockedList);

        return null;
    }
    private void filterRecommendations(Map<String, Object> recommendations, Map<String, List<Object>> blockedList) {
        // Implement filtering based on user's blocked genres and artists
        // TODO: Implement recommendation logic here
    }
}
