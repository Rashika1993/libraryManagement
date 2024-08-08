package com.libraryManagementSystem.library.services;

import com.libraryManagementSystem.library.entities.Book;

import java.util.Map;

public interface BooksService {
    Map<Long, Book> fetchBooksByBookName(String bookName);
    Book fetchBookByBookId(Long bookId);
    Book updateBook(Book book);
    Book addBook(Book book);
    Boolean removeBook(Long bookId);
}
