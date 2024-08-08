package com.library.serviceImpl.inventory;

import com.library.entities.Book;
import com.library.entities.User;
import com.library.enums.Source;
import com.library.services.UserService;
import com.library.services.inventory.InventoryAddition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddInventoryFromDB extends InventoryAddition {

    public AddInventoryFromDB(Source source, Map<Object,Object> map){
        super(source);
        if(map.get("user")!=null) {
            addedBy = (User) map.get("user");
        }
    }
    @Override
    public Map<Book,String> addInventory(List<Book> books) {
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

    @Override
    public List<Book> fetchInventory() {
        return List.of();
    }

}
