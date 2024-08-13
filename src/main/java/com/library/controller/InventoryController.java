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
        InventoryAddition inventoryAddition = InventoryFactory.getSourceService(Source.BULK,objectMap,booksService);
        Map<Book,String> inventoryMap=inventoryAddition.fetchAndAddInventory();
        inventoryMap.entrySet().removeIf(entry -> entry.getKey() == null);
        return ResponseEntity.ok(inventoryMap);
    }
    @PostMapping("/upload/source")
    ResponseEntity<Map<Book,String>> addInventoryFromSource(@RequestParam Source source, @RequestHeader("X-User-Id") Long userId){
        Map<Object, Object> objectMap=new HashMap<>();
        objectMap.put("user",userService.findById(userId));
        InventoryAddition inventoryAddition = InventoryFactory.getSourceService(source,objectMap,booksService);
        Map<Book,String> inventoryMap=inventoryAddition.fetchAndAddInventory();
        inventoryMap.entrySet().removeIf(entry -> entry.getKey() == null);
        return ResponseEntity.ok(inventoryMap);
    }

    @PostMapping
    ResponseEntity<Book>  addBook(@RequestBody Book book, @RequestHeader("X-User-Id") Long userId){
        User user=userService.findById(userId);
        book.setAddedBy(user);
        book.setSource(Source.SYSTEM);
        Book bookAdded=booksService.addBook(book);
        if(bookAdded!=null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookAdded);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bookAdded);
        }
    }
    @GetMapping
    ResponseEntity<List<Book>>  getAllBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(booksService.getAllBooks());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String>  removeBook(@PathVariable Long id){
        Boolean bookRemoved=booksService.removeBook(id);
        if(bookRemoved!=null && bookRemoved) {
            return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deleted the book as book isn't present.");
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
