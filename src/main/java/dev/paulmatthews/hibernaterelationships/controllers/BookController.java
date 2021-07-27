package dev.paulmatthews.hibernaterelationships.controllers;

import dev.paulmatthews.hibernaterelationships.dataRepos.AuthorRepository;
import dev.paulmatthews.hibernaterelationships.dataRepos.BookRepository;
import dev.paulmatthews.hibernaterelationships.models.Author;
import dev.paulmatthews.hibernaterelationships.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping(value = "books")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping
    public String getBooks(Model model) {
        ArrayList<Book> bookList = new ArrayList<>();
        for(Book book : bookRepository.findAll()) {
            bookList.add(book);
        }
        ArrayList<Author> authorList = new ArrayList<>();
        for(Author author : authorRepository.findAll()) {
            authorList.add(author);
        }

        model.addAttribute("books", bookList);
        model.addAttribute("authors", authorList);
        return "books";
    }

    @PostMapping
    public String createBook(Book newBook, Model model) {
        // we save the book like normal
        bookRepository.save(newBook);
        // we have to update the isbn as well because it needs to track the id of the book.

        ArrayList<Book> bookList = new ArrayList<>();
        for(Book book : bookRepository.findAll()) {
            bookList.add(book);
        }
        ArrayList<Author> authorList = new ArrayList<>();
        for(Author author : authorRepository.findAll()) {
            authorList.add(author);
        }

        model.addAttribute("books", bookList);
        model.addAttribute("authors", authorList);
        return "books";
    }
}
