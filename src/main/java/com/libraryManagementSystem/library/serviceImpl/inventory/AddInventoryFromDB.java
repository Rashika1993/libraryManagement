package com.libraryManagementSystem.library.serviceImpl.inventory;

import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.enums.Source;
import com.libraryManagementSystem.library.services.UserService;
import com.libraryManagementSystem.library.services.inventory.InventoryAddition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddInventoryFromDB extends InventoryAddition {
    @Autowired
    UserService userService;
    public AddInventoryFromDB(Source source, Map<Object,Object> map){
        super(source);
        if(map.get("user")!=null) {
            addedBy = userService.findById((Long) map.get("user"));
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
