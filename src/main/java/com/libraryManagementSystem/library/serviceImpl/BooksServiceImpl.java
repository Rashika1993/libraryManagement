package com.libraryManagementSystem.library.serviceImpl;

import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.enums.BookAvailabilityStatus;
import com.libraryManagementSystem.library.services.BooksService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
class BooksServiceImpl implements BooksService {

    Map<String, Map<Long, Book>> bookMap=new HashMap<>();
    Map<Long,Book> bookIdMap=new HashMap<>();
    @Override
    public Map<Long, Book> fetchBooksByBookName(String bookName){
        return bookMap.getOrDefault(bookName,new HashMap<>());
    }

    @Override
    public Book fetchBookByBookId(Long bookId){
        if( bookIdMap.containsKey(bookIdMap)) {
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
        if(book!=null) {
            Book newBook = new Book(book);
            Map<Long,Book> booksMap=new HashMap<>();
            if(bookMap.containsKey(book.getBookName())) {
                booksMap=bookMap.getOrDefault(book.getBookName(), new HashMap<>());
            }
            if(newBook!=null){
                booksMap.put(newBook.getBookId(), newBook);
                bookIdMap.put(newBook.getBookId(),newBook);
                return newBook;
            }
        }
        return null;
    }
    public Boolean removeBook(Long bookId){
        Book book=fetchBookByBookId(bookId);
        if(bookMap.containsKey(book.getBookName()) && bookMap.get(book.getBookName()).containsKey(book.getBookId())){
            Book fetchedbook=bookMap.get(book.getBookName()).get(book.getBookId());
            fetchedbook.setBookAvailabilityStatus(BookAvailabilityStatus.UNAVAILABLE);
            return true;
        }
        return false;
    }

}
