package com.ejemplo.proyecto;

public class Book {
    private String title;
    private String isbn;
    private boolean isAvailable = true;

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void borrow() {
        this.isAvailable = false;
    }
    
    public void returnBook() {
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}