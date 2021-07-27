package dev.paulmatthews.hibernaterelationships.controllers;

import dev.paulmatthews.hibernaterelationships.dataRepos.AuthorRepository;
import dev.paulmatthews.hibernaterelationships.dataRepos.BookRepository;
import dev.paulmatthews.hibernaterelationships.dataRepos.GenreRepository;
import dev.paulmatthews.hibernaterelationships.dataRepos.ISBNRepository;
import dev.paulmatthews.hibernaterelationships.models.Author;
import dev.paulmatthews.hibernaterelationships.models.Book;
import dev.paulmatthews.hibernaterelationships.models.Genre;
import dev.paulmatthews.hibernaterelationships.models.ISBN;
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

    @Autowired
    ISBNRepository isbnRepository;

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
        ArrayList<Author> authorList = new ArrayList<>();
        for(Author author : authorRepository.findAll()) {
            authorList.add(author);
        }
        ArrayList<ISBN> unregisteredIsbns = new ArrayList<ISBN>();
        for(ISBN isbn : isbnRepository.findAll()) {
            if(isbn.getBook() == null) {
                unregisteredIsbns.add(isbn);
            }
        }
        model.addAttribute("books", bookList);
        model.addAttribute("authors", authorList);
        model.addAttribute("unregisteredIsbns", unregisteredIsbns);
        return "books";
    }

    @PostMapping
    public String createBook(Book newBook, Model model) {
        // we save the book like normal
        bookRepository.save(newBook);
        // we have to update the isbn as well because it needs to track the id of the book.
        ISBN isbnUpdate = isbnRepository.findById(newBook.getIsbn().getId()).get();
        isbnUpdate.setBook(newBook);
        isbnRepository.save(isbnUpdate);
        ArrayList<Book> bookList = new ArrayList<>();
        for(Book book : bookRepository.findAll()) {
            bookList.add(book);
        }
        ArrayList<Author> authorList = new ArrayList<>();
        for(Author author : authorRepository.findAll()) {
            authorList.add(author);
        }
        ArrayList<ISBN> unregisteredIsbns = new ArrayList<ISBN>();
        for(ISBN isbn : isbnRepository.findAll()) {
            if(isbn.getBook() == null) {
                unregisteredIsbns.add(isbn);
            }
        }
        model.addAttribute("books", bookList);
        model.addAttribute("authors", authorList);
        model.addAttribute("unregisteredIsbns", unregisteredIsbns);
        return "books";
    }
}
