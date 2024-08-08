package com.library.serviceImpl;

import com.library.entities.Book;
import com.library.entities.User;
import com.library.enums.BookAvailabilityStatus;
import com.library.services.BooksService;
import com.library.services.RentBooksService;
import com.library.services.UserService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    @Autowired
    UserService userService;

    Map<Long, Map<String,Long>> rentBook=new HashMap();
    @Override
    public Map<Book,String> rentBooks(Long userId, List<String> bookNameList) {
        Map<Book,String> bookResultMap=new HashMap<>();
        for(String bookName:bookNameList){
            Book book = null;
            if(!bookName.isBlank()) {
                Map<Long, Book> bookMap = booksService.fetchBooksByBookName(bookName);
                for(Map.Entry<Long, Book> entry:bookMap.entrySet()){
                    if(entry.getValue().getBookAvailabilityStatus()==BookAvailabilityStatus.AVAILABLE){
                        book=entry.getValue();
                        break;
                    }
                }
                if(book!=null){
                    User user=userService.findById(userId);
                    book.setRentUser(user);
                    book.setBookAvailabilityStatus(BookAvailabilityStatus.BOOKED);
                    book.setRentFrom(new Date());
                    booksService.updateBook(book);
                    if(!rentBook.containsKey(user.getUserId())) {
                        rentBook.put(user.getUserId(), new HashMap<>());
                    }
                    rentBook.get(user.getUserId()).put(bookName,book.getBookId());
                    bookResultMap.put(book,"Rented");
                }else {
                    bookResultMap.put(book,"Book isn't available");
                }

            }else{
                bookResultMap.put(book,"Missing Book Name");
            }
        }
        return bookResultMap;
    }

    boolean validateBookDetails(Book book, User user){
        if(book!=null && !book.getBookName().isBlank() && book.getBookId()>0
                && rentBook.containsKey(user.getUserId()) && rentBook.get(user.getUserId()).containsKey(book.getBookName())){
            return true;
        }
        return false;
    }


    @Override
    public Map<Book,String> returnBooks(Long userId,List<Book> bookList) {
        Map<Book,String> bookResultMap=new HashMap<>();
        for(Book book:bookList){
            User user=userService.findById(userId);
            boolean isValid=validateBookDetails(book,user);
            if(isValid) {
                book = booksService.fetchBookByBookId(rentBook.get(user.getUserId()).get(book.getBookName()));
                if(book!=null){
                book.setRentUser(null);
                book.setBookAvailabilityStatus(BookAvailabilityStatus.AVAILABLE);
                book.setRentFrom(null);
                booksService.updateBook(book);
                rentBook.get(user.getUserId()).remove(book.getBookName());
                bookResultMap.put(book,"Returned");
                }else {
                    bookResultMap.put(book,"Book isn't available in DB");
                }
            }else{
                bookResultMap.put(book,"Missing Book Details/Book isn't rented");
            }
        }
        return bookResultMap;
    }
}
