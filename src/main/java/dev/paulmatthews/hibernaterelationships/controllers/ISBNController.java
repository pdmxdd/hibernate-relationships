package dev.paulmatthews.hibernaterelationships.controllers;

import dev.paulmatthews.hibernaterelationships.dataRepos.ISBNRepository;
import dev.paulmatthews.hibernaterelationships.models.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/isbn")
public class ISBNController {
    @Autowired
    ISBNRepository isbnRepository;

    @GetMapping
    public String getISBNForm(Model model) {
        ArrayList<ISBN> allIsbns = new ArrayList<>();
        for(ISBN isbn : isbnRepository.findAll()) {
            allIsbns.add(isbn);
        }
        model.addAttribute("allIsbns", allIsbns);
        return "isbn-form";
    }

    @PostMapping
    public String createISBN(ISBN newIsbn, Model model) {
        isbnRepository.save(newIsbn);
        ArrayList<ISBN> allIsbns = new ArrayList<>();
        for(ISBN isbn : isbnRepository.findAll()) {
            allIsbns.add(isbn);
        }
        model.addAttribute("allIsbns", allIsbns);
        return "isbn-form";
    }
}
