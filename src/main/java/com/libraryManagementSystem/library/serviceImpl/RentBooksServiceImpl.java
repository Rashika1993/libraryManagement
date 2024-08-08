package com.libraryManagementSystem.library.serviceImpl;

import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.entities.User;
import com.libraryManagementSystem.library.enums.BookAvailabilityStatus;
import com.libraryManagementSystem.library.services.BooksService;
import com.libraryManagementSystem.library.services.RentBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("rentBooksServiceImpl")
public class RentBooksServiceImpl implements RentBooksService {

    @Autowired
    BooksService booksService;

    @Override
    public Map<Book,String> rentBooks(User user, List<Book> bookList) {
        Map<Book,String> bookResultMap=new HashMap<>();
        for(Book book:bookList){
            boolean isValid=validateBookDetails(book);
            if(isValid) {
                book = booksService.fetchBookByBookId(book.getBookId());
                if (book.getBookAvailabilityStatus() == BookAvailabilityStatus.AVAILABLE) {
                    book.setRentUser(user);
                    book.setBookAvailabilityStatus(BookAvailabilityStatus.BOOKED);
                    book.setRentFrom(new Date());
                    booksService.updateBook(book);
                    bookResultMap.put(book,"Rented");
                }else {
                    bookResultMap.put(book,"Book isn't available");
                }
            }else{
                bookResultMap.put(book,"Missing Book Details");
            }
        }
        return bookResultMap;
    }

    boolean validateBookDetails(Book book){
        if(book!=null && !book.getBookName().isBlank() && book.getBookId()>0){
            return true;
        }
        return false;
    }


    @Override
    public Map<Book,String> returnBooks(User user,List<Book> bookList) {
        Map<Book,String> bookResultMap=new HashMap<>();
        for(Book book:bookList){
            boolean isValid=validateBookDetails(book);
            if(isValid) {
                book = booksService.fetchBookByBookId(book.getBookId());
                if(book!=null){
                book.setRentUser(null);
                book.setBookAvailabilityStatus(BookAvailabilityStatus.AVAILABLE);
                book.setRentFrom(null);
                booksService.updateBook(book);
                bookResultMap.put(book,"Returned");
                }else {
                    bookResultMap.put(book,"Book isn't available in DB");
                }
            }else{
                bookResultMap.put(book,"Missing Book Details");
            }
        }
        return bookResultMap;
    }
}
