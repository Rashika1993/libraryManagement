package com.library.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseBody
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return new ResponseEntity<>("Unauthorized access: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException(MultipartException e) {
        return new ResponseEntity<>("Failed to parse multipart request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<String> handleGenericException(Exception ex) {
       ex.printStackTrace();
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
