package com.ejemplo.proyecto;

import java.util.ArrayList;

public class Member {
    private String name;
    private String id;
    private ArrayList<Book> borrowedBooks = new ArrayList<Book>();

    public Member(String name, String id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    
    public void borrowBook(Book book) {
        book.borrow();
        borrowedBooks.add(book);
    }
    
    public void returnBook(Book book) {
        book.returnBook();
        borrowedBooks.remove(book);
    }

    // public void getAvailableBooks() {
    //     for (Book book : borrowedBooks) {
    //         if (book.isAvailable()) {
    //             System.out.println(book.getTitle());
    //         }
    //     }
    // }
}
