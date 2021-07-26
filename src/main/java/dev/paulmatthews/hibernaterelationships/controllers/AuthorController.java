package dev.paulmatthews.hibernaterelationships.controllers;

import dev.paulmatthews.hibernaterelationships.dataRepos.AuthorRepository;
import dev.paulmatthews.hibernaterelationships.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/authors")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    public ArrayList<Author> getAllAuthors() {
        ArrayList<Author> authors = new ArrayList<>();
        for(Author author : authorRepository.findAll()) {
            authors.add(author);
        }
        return authors;
    }

    @GetMapping
    public String getAuthors(Model model) {
        model.addAttribute("authors", this.getAllAuthors());
        return "authors";
    }

    @PostMapping
    public String createAuthor(Author newAuthor, Model model) {
        authorRepository.save(newAuthor);
        model.addAttribute("authors", this.getAllAuthors());
        return "authors";
    }
}
