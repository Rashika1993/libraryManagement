package com.library.services.inventory;

import com.library.entities.Book;
import com.library.entities.User;
import com.library.enums.Source;
import com.library.services.BooksService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


abstract public class InventoryAddition {

    public BooksService booksService;
    public Source source;
    public User addedBy;

    public List<Book> books;
    public InventoryAddition(Source source, Map<Object,Object> map){
        this.source= source;
        this.books=new ArrayList<>();
        if(map.get("user")!=null) {
            this.addedBy = (User) map.get("user");
        }
        this.booksService= (BooksService) map.get("booksService");
    }
    public abstract Map<Book,String> addInventory(List<Book> books);
    public abstract List<Book> fetchInventory();


}
