package com.mariannadreams.service;

import com.mariannadreams.dto.SpotifyImportDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {

    public SpotifyImportDTO fetchAlbumData(String url) throws Exception {
        return fetchEmbedData(url);
    }

    public SpotifyImportDTO fetchTrackData(String url) throws Exception {
        return fetchEmbedData(url);
    }

    private SpotifyImportDTO fetchEmbedData(String url) throws Exception {
        if (url == null || (!url.contains("open.spotify.com/album/") && !url.contains("open.spotify.com/track/"))) {
            throw new IllegalArgumentException("Invalid Spotify URL. Must be an open.spotify.com album or track URL.");
        }

        // Convert normal URL to Embed URL which is not blocked by Spotify scrapers
        String embedUrl = url;
        if (url.contains("/album/")) {
            embedUrl = "https://open.spotify.com/embed/album/" + extractId(url, "/album/");
        } else if (url.contains("/track/")) {
            embedUrl = "https://open.spotify.com/embed/track/" + extractId(url, "/track/");
        }

        Document doc = Jsoup.connect(embedUrl)
                .userAgent("Mozilla/5.0")
                .get();

        String nextData = doc.select("script#__NEXT_DATA__").html();
        if (nextData == null || nextData.isEmpty()) {
            throw new RuntimeException("Could not find Spotify metadata on page.");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(nextData);
        JsonNode entity = root.at("/props/pageProps/state/data/entity");

        if (entity.isMissingNode()) {
            throw new RuntimeException("Could not find album/track data. Are you sure this URL is correct? Spotify returned 'Page not found'.");
        }

        SpotifyImportDTO dto = new SpotifyImportDTO();
        
        String title = entity.path("title").asText(null);
            if (title != null && !title.equals("null")) {
                dto.setTitle(title);
            }
            
            JsonNode images = entity.at("/visualIdentity/image");
            if (images.isArray() && images.size() > 0) {
                // Usually the last image is the largest one
                String imageUrl = images.get(images.size() - 1).path("url").asText(null);
                if (imageUrl != null && !imageUrl.equals("null")) {
                    dto.setImageUrl(imageUrl);
                }
            }
            
            String releaseDate = entity.path("releaseDate").asText(null);
            if (releaseDate != null && releaseDate.length() >= 4 && !releaseDate.equals("null")) {
                try {
                    dto.setReleaseYear(Integer.parseInt(releaseDate.substring(0, 4)));
                } catch (NumberFormatException ignored) {}
            }
            
            JsonNode trackList = entity.at("/trackList");
            if (trackList.isArray()) {
                List<SpotifyImportDTO> tracks = new ArrayList<>();
                for (JsonNode tNode : trackList) {
                    SpotifyImportDTO tDto = new SpotifyImportDTO();
                    String tTitle = tNode.path("title").asText(null);
                    if (tTitle != null && !tTitle.equals("null")) tDto.setTitle(tTitle);
                    
                    String uri = tNode.path("uri").asText(null);
                    if (uri != null && uri.startsWith("spotify:track:")) {
                        String tId = uri.substring("spotify:track:".length());
                        tDto.setSpotifyUrl("https://open.spotify.com/track/" + tId);
                        tDto.setEmbedUrl("https://open.spotify.com/embed/track/" + tId);
                    }
                    tracks.add(tDto);
                }
                dto.setTracks(tracks);
            }
            
        dto.setEmbedUrl(embedUrl);
        return dto;
    }

    private String extractId(String url, String pathInfo) {
        int idx = url.indexOf(pathInfo) + pathInfo.length();
        String id = url.substring(idx);
        if (id.contains("?")) {
            id = id.substring(0, id.indexOf("?"));
        }
        if (id.contains("/")) {
            id = id.substring(0, id.indexOf("/"));
        }
        return id;
    }
}
