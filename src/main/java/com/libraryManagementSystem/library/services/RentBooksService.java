package com.libraryManagementSystem.library.services;

import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.entities.User;

import java.util.List;
import java.util.Map;

public interface RentBooksService {
    Map<Book,String> rentBooks(User user, List<Book> bookList);
    Map<Book,String> returnBooks(User user, List<Book> bookList);
}
