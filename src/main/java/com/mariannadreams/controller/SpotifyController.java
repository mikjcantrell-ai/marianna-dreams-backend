package com.mariannadreams.controller;

import com.mariannadreams.dto.SpotifyImportDTO;
import com.mariannadreams.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/spotify")
public class SpotifyController {

    private final SpotifyService spotifyService;

    @Autowired
    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/album")
    public ResponseEntity<?> fetchAlbum(@RequestParam("url") String url) {
        try {
            return ResponseEntity.ok(spotifyService.fetchAlbumData(url));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/track")
    public ResponseEntity<?> fetchTrack(@RequestParam("url") String url) {
        try {
            return ResponseEntity.ok(spotifyService.fetchTrackData(url));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
