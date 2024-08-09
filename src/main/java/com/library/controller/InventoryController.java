package com.library.controller;

import com.library.entities.Book;
import com.library.entities.User;
import com.library.enums.Source;
import com.library.factories.InventoryFactory;
import com.library.services.BooksService;
import com.library.services.UserService;
import com.library.services.inventory.InventoryAddition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class InventoryController {
    @Autowired
    BooksService booksService;

    @Autowired
    UserService userService;

    @PostMapping("/upload/bulk")
    ResponseEntity<Map<Book,String>> addInventoryInBulk(@RequestPart("file") MultipartFile file, @RequestHeader("X-User-Id") Long userId){
        Map<Object, Object> objectMap=new HashMap<>();
        objectMap.put("file",file);
        objectMap.put("user",userService.findById(userId));
        objectMap.put("booksService",booksService);
        InventoryAddition inventoryAddition = InventoryFactory.getSourceService(Source.DEFAULT,objectMap);
        List<Book> bookList = inventoryAddition.fetchInventory();
        Map<Book,String> inventoryMap=inventoryAddition.addInventory(bookList);
        return ResponseEntity.ok(inventoryMap);
    }
    @PostMapping("/upload/source")
    ResponseEntity<Map<Book,String>> addInventoryFromSource(@RequestParam Source source, @RequestHeader("X-User-Id") Long userId){
        Map<Object, Object> objectMap=new HashMap<>();
        objectMap.put("user",userService.findById(userId));
        objectMap.put("booksService",booksService);
        InventoryAddition inventoryAddition = InventoryFactory.getSourceService(source,objectMap);
        List<Book> bookList = inventoryAddition.fetchInventory();
        Map<Book,String> inventoryMap=inventoryAddition.addInventory(bookList);
        return ResponseEntity.ok(inventoryMap);
    }

    @PostMapping("/books")
    ResponseEntity<String>  addBook(@RequestBody Book book, @RequestHeader("X-User-Id") Long userId){
        User user=userService.findById(userId);
        book.setAddedBy(user);
        Book bookAdded=booksService.addBook(book);
        if(bookAdded!=null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add the book.");
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String>  removeBook(@PathVariable Long id){
        Boolean bookRemoved=booksService.removeBook(id);
        if(bookRemoved!=null) {
            return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deleted the book.");
        }
    }
    @GetMapping("/{id}")
    ResponseEntity<Book>  fetchBooksByBookId(@PathVariable Long id){
        Book book=booksService.fetchBookByBookId(id);
        if(book!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(book);
        }
    }
}
