package com.library.services;

import com.library.entities.Book;

import java.util.List;
import java.util.Map;

public interface BooksService {
    Map<Long, Book> fetchBooksByBookName(String bookName);
    Book fetchBookByBookId(Long bookId);
    Book updateBook(Book book);
    Book addBook(Book book);
    Boolean removeBook(Long bookId);
    List<Book> getAllBooks();
}
