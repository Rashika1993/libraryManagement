package com.libraryManagementSystem.library.serviceImpl.inventory;
import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.enums.Source;
import com.libraryManagementSystem.library.services.UserService;
import com.libraryManagementSystem.library.services.inventory.InventoryAddition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class AddBulkInventory extends InventoryAddition {
    MultipartFile multipartFile;
    @Autowired
    UserService userService;
    public AddBulkInventory(Source source, Map<Object,Object> map){
        super(source);
        if(map.get("user")!=null) {
            addedBy = userService.findById((Long) map.get("user"));
        }
        if(map.get("file")!=null) {
            multipartFile = (MultipartFile) map.get("file");
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
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 2) {
                    String bookName = fields[0].trim();
                    Book book=new Book();
                    book.setBookName(bookName);
                    book.setAddedBy(addedBy);
                    book.setSource(source);
                    books.add(new Book(book));
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception as needed
        }
        return books;
    }
}
