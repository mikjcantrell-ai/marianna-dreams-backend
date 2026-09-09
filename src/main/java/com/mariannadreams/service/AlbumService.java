package com.mariannadreams.service;

import com.mariannadreams.model.Album;
import com.mariannadreams.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAllByOrderByDisplayOrderAsc();
    }

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found: " + id));
    }

    public Album updateAlbum(Long id, Album updated) {
        Album existing = getAlbumById(id);
        existing.setTitle(updated.getTitle());
        existing.setReleaseYear(updated.getReleaseYear());
        existing.setImageUrl(updated.getImageUrl());
        existing.setSpotifyUrl(updated.getSpotifyUrl());
        existing.setDescription(updated.getDescription());
        existing.setDisplayOrder(updated.getDisplayOrder());
        return albumRepository.save(existing);
    }

    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}
