package com.libraryManagementSystem.library.controller;

import com.libraryManagementSystem.library.entities.Book;
import com.libraryManagementSystem.library.entities.User;
import com.libraryManagementSystem.library.services.RentBooksService;
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
   public ResponseEntity<Map<Book,String>> rentBooks(List<Book> books, User user){
       Map<Book,String> rentBooksMap= rentBooksService.rentBooks(user,books);
       if(!rentBooksMap.isEmpty()) {
           return ResponseEntity.ok(rentBooksMap);
       }else{
           return ResponseEntity.notFound().build();
       }
    }

    @PostMapping("/return/books")
    public ResponseEntity<Map<Book,String>> returnBooks(List<Book> books, User user){
        Map<Book,String> returnBookMap= rentBooksService.returnBooks(user,books);
        if(!returnBookMap.isEmpty()) {
            return ResponseEntity.ok(returnBookMap);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
