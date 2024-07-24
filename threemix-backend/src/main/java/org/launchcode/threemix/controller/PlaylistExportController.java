package org.launchcode.threemix.controller;

import org.launchcode.threemix.model.BlockedArtist;
import org.launchcode.threemix.model.BlockedSong;
import org.launchcode.threemix.model.User;
import org.launchcode.threemix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PlaylistExportController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/generateTrackList", produces = "application/json")
    public ResponseEntity<Map<String, Object>> generateTrackList(@CookieValue("accessToken") String accessToken,
                                                                 @RequestParam List<String> chosenGenres,
                                                                 @RequestParam String username) {
        // Get user by username
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        // Fetch blocked artists and songs from the database
        Map<String, List<String>> blockedList = getBlockedList(user);

        // Generate recommendations from Spotify API
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String, Object> trackRecommendations = getRecommendations(chosenGenres, entity);

        // Filter out blocked artists and songs
        filterRecommendations(trackRecommendations, blockedList);

        return ResponseEntity.ok(trackRecommendations);
    }

    // Fetch blocked artists and songs from the database
    private Map<String, List<String>> getBlockedList(User user) {
        List<String> blockedArtists = userService.getBlockedArtists(user).stream()
                .map(BlockedArtist::getName)
                .collect(Collectors.toList());
        List<String> blockedSongs = userService.getBlockedSongs(user).stream()
                .map(BlockedSong::getTitle)
                .collect(Collectors.toList());

        Map<String, List<String>> blockedList = new HashMap<>();
        blockedList.put("artists", blockedArtists);
        blockedList.put("songs", blockedSongs);

        return blockedList;
    }

    // Method to fetch recommendations from Spotify API
    private Map<String, Object> getRecommendations(List<String> chosenGenres, HttpEntity<String> entity) {
        // Build the Spotify API request URL with chosen genres
        String url = buildSpotifyRecommendationUrl(chosenGenres);

        // Fetch recommendations from Spotify API
        return restTemplate.getForObject(url, Map.class, entity);
    }

    // Method to build the Spotify recommendation URL
    private String buildSpotifyRecommendationUrl(List<String> chosenGenres) {
        String genres = String.join(",", chosenGenres);
        return "https://api.spotify.com/v1/recommendations?seed_genres=" + genres;
    }

    // Method to filter out blocked artists and songs
    private void filterRecommendations(Map<String, Object> recommendations, Map<String, List<String>> blockedList) {
        List<Map<String, Object>> tracks = (List<Map<String, Object>>) recommendations.get("tracks");

        List<String> blockedArtists = blockedList.get("artists");
        List<String> blockedSongs = blockedList.get("songs");

        List<Map<String, Object>> filteredTracks = tracks.stream()
                .filter(track -> {
                    Map<String, Object> trackDetails = (Map<String, Object>) track;
                    List<Map<String, Object>> artists = (List<Map<String, Object>>) trackDetails.get("artists");
                    String trackId = (String) trackDetails.get("id");

                    boolean isBlockedArtist = artists.stream()
                            .map(artist -> (String) artist.get("id"))
                            .anyMatch(blockedArtists::contains);

                    boolean isBlockedSong = blockedSongs.contains(trackId);

                    return !isBlockedArtist && !isBlockedSong;
                })
                .collect(Collectors.toList());

        recommendations.put("tracks", filteredTracks);
    }
}