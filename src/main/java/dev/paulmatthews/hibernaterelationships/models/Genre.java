package dev.paulmatthews.hibernaterelationships.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Genre {
    @Id
    @GeneratedValue
    public int id;
    public String type;

    @ManyToMany(mappedBy = "genres")
    public List<Book> books;

    public Genre() {}

    public void addBook(Book book) {
        books.add(book);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
