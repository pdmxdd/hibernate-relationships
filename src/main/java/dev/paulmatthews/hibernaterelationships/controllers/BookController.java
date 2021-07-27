package dev.paulmatthews.hibernaterelationships.controllers;

import dev.paulmatthews.hibernaterelationships.dataRepos.BookRepository;
import dev.paulmatthews.hibernaterelationships.dataRepos.GenreRepository;
import dev.paulmatthews.hibernaterelationships.models.Book;
import dev.paulmatthews.hibernaterelationships.models.Genre;
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
    GenreRepository genreRepository;

    @PostMapping(value = "/{bookId}")
    public String addBookGenreById(@PathVariable int bookId, @RequestParam int genreId, Model model) {
        // get the book by bookId
        Optional<Book> maybeBook = bookRepository.findById(bookId);

        // check that the book exists:
        if(maybeBook.isEmpty()) {
            return "book-by-id";
        }
        // unpack the book
        Book theBook = maybeBook.get();

        // get the genre by genreId
        Optional<Genre> maybeGenre = genreRepository.findById(genreId);

        // check that the genre exists:
        if(maybeGenre.isEmpty()) {
            return "book-by-id";
        }
        // unpack the genre:
        Genre theGenre = maybeGenre.get();

        // add the genre to the book
        theBook.addGenre(theGenre);

        // add the book to the genre
        theGenre.addBook(theBook);

        // save them
        bookRepository.save(theBook);
        genreRepository.save(theGenre);

        ArrayList<Genre> allGenres = new ArrayList<>();
        for(Genre genre : genreRepository.findAll()) {
            allGenres.add(genre);
        }
        model.addAttribute("book", theBook);
        model.addAttribute("allGenres", allGenres);
        return "book-by-id";
    }

    @GetMapping(value = "/{bookId}")
    public String getBookById(@PathVariable int bookId, Model model) {
        Optional<Book> maybeBook = bookRepository.findById(bookId);
        if(maybeBook.isEmpty()) {
            model.addAttribute("book", "not found");
            return "book-by-id";
        }
        Book foundBook = maybeBook.get();
        ArrayList<Genre> allGenres = new ArrayList<>();
        for(Genre genre : genreRepository.findAll()) {
            allGenres.add(genre);
        }
        model.addAttribute("book", foundBook);
        model.addAttribute("allGenres", allGenres);

        return "book-by-id";
    }

    @GetMapping
    public String getBooks(Model model) {
        ArrayList<Book> bookList = new ArrayList<>();
        for(Book book : bookRepository.findAll()) {
            bookList.add(book);
        }

        model.addAttribute("books", bookList);
        return "books";
    }

    @PostMapping
    public String createBook(Book newBook, Model model) {
        bookRepository.save(newBook);

        ArrayList<Book> bookList = new ArrayList<>();
        for(Book book : bookRepository.findAll()) {
            bookList.add(book);
        }

        model.addAttribute("books", bookList);
        return "books";
    }
}
