package com.libraryManagementSystem.library.controller;

import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.enums.Source;
import com.libraryManagementSystem.library.factory.InventoryFactory;
import com.libraryManagementSystem.library.services.BooksService;
import com.libraryManagementSystem.library.services.inventory.InventoryAddition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class InventoryController {
    BooksService booksService;

    @PostMapping("/upload/bulk")
    ResponseEntity<Map<Book,String>> addInventoryInBulk(@RequestParam MultipartFile file, @RequestParam Long userId){
        Map<Object, Object> objectMap=new HashMap<>();
        objectMap.put("file",file);
        objectMap.put("user",userId);
        InventoryAddition inventoryAddition = InventoryFactory.source(Source.DEFAULT,objectMap);
        List<Book> bookList = inventoryAddition.fetchInventory();
        Map<Book,String> inventoryMap=inventoryAddition.addInventory(bookList);
        return ResponseEntity.ok(inventoryMap);
    }
    @PostMapping("/upload/source")
    ResponseEntity<Map<Book,String>> addInventoryFromSource(@RequestParam Source source, @RequestParam Long userId){
        Map<Object, Object> objectMap=new HashMap<>();
        objectMap.put("user",userId);
        InventoryAddition inventoryAddition = InventoryFactory.source(source,objectMap);
        List<Book> bookList = inventoryAddition.fetchInventory();
        Map<Book,String> inventoryMap=inventoryAddition.addInventory(bookList);
        return ResponseEntity.ok(inventoryMap);
    }

    @PostMapping("/books")
    ResponseEntity<String>  addBook(@RequestBody Book book){
        Book bookAdded=booksService.addBook(book);
        if(bookAdded!=null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add the book.");
        }
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<String>  removeBook(@PathVariable Long id){
        Boolean bookRemoved=booksService.removeBook(id);
        if(bookRemoved!=null) {
            return ResponseEntity.status(HttpStatus.OK).body("Book added successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add the book.");
        }
    }
    @GetMapping("/books/{id}")
    ResponseEntity<String>  fetchBooksByBookId(@PathVariable Long id){
        Book book=booksService.fetchBookByBookId(id);
        if(book!=null) {
            return ResponseEntity.status(HttpStatus.OK).body("Book added successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book Not Found!");
        }
    }
}
