package dev.paulmatthews.hibernaterelationships.models;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private int id;
    private String title;

    @ManyToOne
    private Author author;

    public Book() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
