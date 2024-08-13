package com.library.strategies;

import com.library.entities.Book;
import com.library.entities.User;
import com.library.enums.Source;
import com.library.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class ProcessingStrategy {
    BooksService booksService;
    ProcessingStrategy(BooksService booksService){
        this.booksService=booksService;
    }
    public abstract Map<Book,String> process(BufferedReader br, User addedBy, Source source) throws IOException;
}
