package com.library.serviceImpl;

import com.library.entities.Book;
import com.library.enums.BookAvailabilityStatus;
import com.library.services.BooksService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
class BooksServiceImpl implements BooksService {
    AtomicLong bookId=new AtomicLong(0l);
    Map<String, Map<Long, Book>> bookMap=new HashMap<>();
    Map<Long,Book> bookIdMap=new HashMap<>();
    @Override
    public Map<Long, Book> fetchBooksByBookName(String bookName){
        return bookMap.getOrDefault(bookName,new HashMap<>());
    }

    @Override
    public Book fetchBookByBookId(Long bookId){
        if( bookIdMap.containsKey(bookId)) {
            return bookIdMap.get(bookId);
        }
        return null;
    }

    public Book updateBook(Book book){
        if(bookMap.containsKey(book.getBookName()) && bookMap.get(book.getBookName()).containsKey(book.getBookId())) {
            bookMap.get(book.getBookName()).put(book.getBookId(),book);
            return bookMap.get(book.getBookName()).get(book.getBookId());
        }
        return null;
    }
    public Book addBook(Book book){
        if(book!=null && book.getBookName()!=null && book.getBookName().trim()!="") {
            Book newBook = new Book(book);
            newBook.setBookId(bookId.incrementAndGet());
            if(!bookMap.containsKey(book.getBookName())){
                bookMap.put(book.getBookName(), new HashMap<>());
            }
            if(newBook!=null){
                bookMap.get(newBook.getBookName()).put(newBook.getBookId(),newBook);
                bookIdMap.put(newBook.getBookId(),newBook);
                return newBook;
            }
        }
        return null;
    }
    public Boolean removeBook(Long bookId){
        Book book=fetchBookByBookId(bookId);
        if(book!=null && bookMap.containsKey(book.getBookName()) && bookMap.get(book.getBookName()).containsKey(book.getBookId())){
            Book fetchedbook=bookMap.get(book.getBookName()).get(book.getBookId());
            fetchedbook.setBookAvailabilityStatus(BookAvailabilityStatus.UNAVAILABLE);
            return true;
        }
        return false;
    }
    public List<Book> getAllBooks(){
        if (bookIdMap!=null && !bookIdMap.isEmpty()) {
            return bookIdMap.values().stream().toList();
        }
        return new ArrayList<>();
    }


}
