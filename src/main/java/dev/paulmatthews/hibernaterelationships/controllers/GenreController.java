package dev.paulmatthews.hibernaterelationships.controllers;

import dev.paulmatthews.hibernaterelationships.dataRepos.GenreRepository;
import dev.paulmatthews.hibernaterelationships.models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(value = "/genres")
public class GenreController {
    @Autowired
    GenreRepository genreRepository;

    @GetMapping(value = "/{id}")
    public String displayBooksByGenreId(@PathVariable int id, Model model) {
        Optional<Genre> maybeGenre = genreRepository.findById(id);
        if(maybeGenre.isEmpty()) {
            return "redirect:/genres";
        }
        Genre selectedGenre = maybeGenre.get();
        model.addAttribute("genre", selectedGenre);
        return "genre-books";
    }

    @GetMapping
    public String getGenres(Model model) {
        ArrayList<Genre> genres = new ArrayList<>();
        for(Genre genre : genreRepository.findAll()) {
            genres.add(genre);
        }
        model.addAttribute("genres", genres);
        return "genres";
    }

    @PostMapping
    public String postGenre(Genre newGenre, Model model) {
        genreRepository.save(newGenre);
        ArrayList<Genre> genres = new ArrayList<>();
        for(Genre genre : genreRepository.findAll()) {
            genres.add(genre);
        }
        model.addAttribute("genres", genres);
        return "genres";
    }
}
