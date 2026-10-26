package ru.spbume.musicalbums.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbume.musicalbums.entity.Album;
import ru.spbume.musicalbums.entity.User;
import ru.spbume.musicalbums.repository.AlbumRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAlbumsForUser(User user, String genre) {
        if (genre != null && !genre.isEmpty()) {
            return albumRepository.findByUserAndGenre(user, genre);
        }
        return albumRepository.findByUser(user);
    }

    public void saveAlbum(Album album) {
        albumRepository.save(album);
    }

    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}
