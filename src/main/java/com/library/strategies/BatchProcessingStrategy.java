package com.library.strategies;

import com.library.entities.Book;
import com.library.entities.User;
import com.library.enums.Source;
import com.library.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchProcessingStrategy extends ProcessingStrategy {

    //Currently the inventory addition is being done synchronously
    // However, we can batch process it.
    // We can align multiple worker threads which will run in parallel
    // which will help in reducing latency and utilising CPU cores efficiently
    public BatchProcessingStrategy(BooksService booksService){
       super(booksService);
    }
    @Override
    public Map<Book,String> process(BufferedReader br, User addedBy, Source source) throws IOException {
        String line;
        br.readLine();
        List<Book> books = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields.length >= 1) {
                String bookName = fields[0].trim();
                Book book=new Book();
                book.setBookName(bookName);
                book.setAddedBy(addedBy);
                book.setSource(source);
                books.add(new Book(book));
            }
        }
        Map<Book,String> bookInventory=new HashMap<>();
        for(Book book:books) {
            Book addedBook=booksService.addBook(book);
            if(addedBook!=null){
                bookInventory.put(addedBook,"Book Addition Successful!");
            }else {
                bookInventory.put(addedBook,"Book Addition Failed!");
            }
        }
        return bookInventory;
    }
}
