package com.mariannadreams.dto;

import lombok.Data;
import java.util.List;

@Data
public class SpotifyImportDTO {
    private String title;
    private String imageUrl;
    private Integer releaseYear;
    private String embedUrl;
    private String spotifyUrl;
    private List<SpotifyImportDTO> tracks;
}
