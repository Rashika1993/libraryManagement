package com.library.controller;

import com.library.entities.Book;
import com.library.services.RentBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RentBooksController {
    @Autowired
    RentBooksService rentBooksService;

   @PostMapping("/rent/books")
   public ResponseEntity<Map<Book,String>> rentBooks(@RequestBody List<String> bookNames, @RequestHeader("X-User-Id") Long userId){
       Map<Book,String> rentBooksMap= rentBooksService.rentBooks(userId,bookNames);
       if(!rentBooksMap.isEmpty()) {
           rentBooksMap.entrySet().removeIf(entry -> entry.getKey() == null);
           return ResponseEntity.ok(rentBooksMap);
       }else{
           return ResponseEntity.notFound().build();
       }
    }

    @PostMapping("/return/books")
    public ResponseEntity<Map<Book,String>> returnBooks(@RequestBody List<Book> bookList, @RequestHeader("X-User-Id") Long userId){
        Map<Book,String> returnBookMap= rentBooksService.returnBooks(userId,bookList);
        if(!returnBookMap.isEmpty()) {
            returnBookMap.entrySet().removeIf(entry -> entry.getKey() == null);
            return ResponseEntity.ok(returnBookMap);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
