package dev.paulmatthews.hibernaterelationships.models;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private int id;
    private String title;

    @OneToOne
    private ISBN isbn;

    public ISBN getIsbn() {
        return isbn;
    }

    public void setIsbn(ISBN isbn) {
        this.isbn = isbn;
    }

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

}
