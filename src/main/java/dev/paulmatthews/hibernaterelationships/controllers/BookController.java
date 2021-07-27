package dev.paulmatthews.hibernaterelationships.controllers;

import dev.paulmatthews.hibernaterelationships.dataRepos.AuthorRepository;
import dev.paulmatthews.hibernaterelationships.dataRepos.BookRepository;
import dev.paulmatthews.hibernaterelationships.dataRepos.ISBNRepository;
import dev.paulmatthews.hibernaterelationships.models.Author;
import dev.paulmatthews.hibernaterelationships.models.Book;
import dev.paulmatthews.hibernaterelationships.models.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "books")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    ISBNRepository isbnRepository;

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
