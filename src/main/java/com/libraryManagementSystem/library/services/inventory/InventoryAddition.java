package com.libraryManagementSystem.library.services.inventory;

import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.entities.User;
import com.libraryManagementSystem.library.enums.Source;
import com.libraryManagementSystem.library.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

abstract public class InventoryAddition {
    @Autowired
    public BooksService booksService;
    public Source source;
    public User addedBy;

    public List<Book> books;
    public InventoryAddition(Source source){
        this.source= this.source;
    }
    public abstract Map<Book,String> addInventory(List<Book> books);
    public abstract List<Book> fetchInventory();


}
