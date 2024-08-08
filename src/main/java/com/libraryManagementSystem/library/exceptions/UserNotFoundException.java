package com.libraryManagementSystem.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        handleUserNotFoundException(message);
    }

    public ResponseEntity<String> handleUserNotFoundException(String message) {
        System.out.println("Not Found");
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
