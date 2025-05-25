package com.ejemplo.proyecto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookManager {
    private List<Book> books;
    private Map<String, Member> members;
    
    public BookManager() {
        this.books = new ArrayList<>();
        this.members = new HashMap<>();
    }
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    public void registerMember(Member member) {
        members.put(member.getId(), member);
    }
    
    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
    
    public Member findMemberById(String id) {
        return members.get(id);
    }
    
    public boolean lendBook(String memberId, String isbn) {
        Member member = findMemberById(memberId);
        Book book = findBookByIsbn(isbn);
        
        if (member != null && book != null && book.isAvailable()) {
            member.borrowBook(book);
            return true;
        }
        return false;
    }
    
public boolean returnBook(String memberId, String isbn) {
    Member member = findMemberById(memberId);
    Book book = findBookByIsbn(isbn);

    if (member != null && book != null && !book.isAvailable() && member.getBorrowedBooks().contains(book)) {
        member.returnBook(book);
        return true;
    }
    return false;
}
    
    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }
}