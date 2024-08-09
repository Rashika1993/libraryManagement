package com.library.services;

import com.library.entities.Book;
import java.util.List;
import java.util.Map;

public interface RentBooksService {
    Map<Book,String> rentBooks(Long userId, List<String> bookNameList);
    Map<Book,String> returnBooks(Long userId, List<Book> bookList);
}
