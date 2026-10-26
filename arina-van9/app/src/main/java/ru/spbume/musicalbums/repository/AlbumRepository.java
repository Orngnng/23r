package ru.spbume.musicalbums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbume.musicalbums.entity.Album;
import ru.spbume.musicalbums.entity.User;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    
    List<Album> findByUser(User user);
    
    List<Album> findByUserAndGenre(User user, String genre);
}
