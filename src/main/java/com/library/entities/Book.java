package com.library.entities;

import com.library.enums.BookAvailabilityStatus;
import com.library.enums.Source;
import lombok.Data;

import java.util.Date;

@Data
public class Book {

    Long bookId=0l;
    String bookName;
    Source source;
    BookAvailabilityStatus bookAvailabilityStatus;
    Date rentFrom;
    User rentUser;
    User addedBy;

    public Book(){

    }
    public Book(Source source,String bookName){
        this.source=source;
        this.bookName=bookName;
        this.bookAvailabilityStatus=BookAvailabilityStatus.AVAILABLE;
    }

    public Book(Book book){
        this.bookName=book.getBookName();
        this.source=book.getSource();
        this.addedBy=book.getAddedBy();
        this.bookAvailabilityStatus=BookAvailabilityStatus.AVAILABLE;
    }


}
