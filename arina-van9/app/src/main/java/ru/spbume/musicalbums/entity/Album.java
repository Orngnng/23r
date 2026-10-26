package ru.spbume.musicalbums.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название альбома не может быть пустым")
    @Size(max = 200, message = "Название альбома не должно превышать 200 символов")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Исполнитель не может быть пустым")
    @Size(max = 200, message = "Имя исполнителя не должно превышать 200 символов")
    @Column(nullable = false)
    private String artist;

    @NotNull(message = "Год выпуска обязателен")
    @Min(value = 1900, message = "Год выпуска не может быть раньше 1900")
    @Max(value = 2100, message = "Год выпуска не может быть позже 2100")
    @Column(name = "release_year", nullable = false)
    private Integer year;

    @NotBlank(message = "Жанр обязателен")
    @Column(nullable = false)
    private String genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Album() {
    }

    public Album(String title, String artist, Integer year, String genre, User user) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
