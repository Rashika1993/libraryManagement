package com.libraryManagementSystem.library.entities;

import com.libraryManagementSystem.library.enums.BookAvailabilityStatus;
import com.libraryManagementSystem.library.enums.Source;
import lombok.Data;

import java.util.Date;

@Data
public class Book {

    Long bookId;
    String bookName;
    Source source;
    BookAvailabilityStatus bookAvailabilityStatus;
    Date rentFrom;
    User rentUser;
    User addedBy;

    Long generateId(){
        return ++bookId;
    }
    public Book(){

    }
    public Book(Source source,String bookName){
        this.source=source;
        this.bookName=bookName;
        this.bookAvailabilityStatus=BookAvailabilityStatus.AVAILABLE;
    }

    public Book(Book book){
        this.bookId=generateId();
        this.bookName=book.getBookName();
        this.source=book.getSource();
        this.addedBy=book.getAddedBy();
        this.bookAvailabilityStatus=BookAvailabilityStatus.AVAILABLE;
    }


}
