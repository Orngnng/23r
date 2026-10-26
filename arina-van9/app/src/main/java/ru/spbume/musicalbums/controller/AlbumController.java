package ru.spbume.musicalbums.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spbume.musicalbums.entity.Album;
import ru.spbume.musicalbums.entity.User;
import ru.spbume.musicalbums.service.AlbumService;
import ru.spbume.musicalbums.service.UserService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final UserService userService;

    private static final List<String> GENRES = Arrays.asList("Рок", "Поп", "Джаз", "Классика", "Хип-хоп");

    public AlbumController(AlbumService albumService, UserService userService) {
        this.albumService = albumService;
        this.userService = userService;
    }

    @GetMapping
    public String listAlbums(@RequestParam(required = false) String genre,
                             HttpSession session,
                             Model model) {
        User currentUser = userService.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<Album> albums = albumService.getAlbumsForUser(currentUser, genre);
        model.addAttribute("albums", albums);
        model.addAttribute("genres", GENRES);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("userLogin", session.getAttribute("userLogin"));

        return "albums";
    }

    @GetMapping("/add")
    public String showAddForm(HttpSession session, Model model) {
        User currentUser = userService.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("album", new Album());
        model.addAttribute("genres", GENRES);
        return "album-form";
    }

    @PostMapping("/add")
    public String addAlbum(@Valid @ModelAttribute("album") Album album,
                           BindingResult result,
                           HttpSession session,
                           Model model) {
        User currentUser = userService.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("genres", GENRES);
            return "album-form";
        }

        album.setUser(currentUser);
        albumService.saveAlbum(album);
        return "redirect:/albums";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               HttpSession session,
                               Model model) {
        User currentUser = userService.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        Album album = albumService.getAlbumById(id)
                .filter(a -> a.getUser().getId().equals(currentUser.getId()))
                .orElse(null);

        if (album == null) {
            return "redirect:/albums";
        }

        model.addAttribute("album", album);
        model.addAttribute("genres", GENRES);
        return "album-form";
    }

    @PostMapping("/edit/{id}")
    public String updateAlbum(@PathVariable Long id,
                              @Valid @ModelAttribute("album") Album album,
                              BindingResult result,
                              HttpSession session,
                              Model model) {
        User currentUser = userService.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("genres", GENRES);
            return "album-form";
        }

        Album existingAlbum = albumService.getAlbumById(id)
                .filter(a -> a.getUser().getId().equals(currentUser.getId()))
                .orElse(null);

        if (existingAlbum == null) {
            return "redirect:/albums";
        }

        existingAlbum.setTitle(album.getTitle());
        existingAlbum.setArtist(album.getArtist());
        existingAlbum.setYear(album.getYear());
        existingAlbum.setGenre(album.getGenre());

        albumService.saveAlbum(existingAlbum);
        return "redirect:/albums";
    }

    @PostMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable Long id, HttpSession session) {
        User currentUser = userService.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        Album album = albumService.getAlbumById(id)
                .filter(a -> a.getUser().getId().equals(currentUser.getId()))
                .orElse(null);

        if (album != null) {
            albumService.deleteAlbum(id);
        }

        return "redirect:/albums";
    }
}
