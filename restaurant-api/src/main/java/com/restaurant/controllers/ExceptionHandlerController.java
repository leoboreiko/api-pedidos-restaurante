package com.restaurant.controllers;

import com.restaurant.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<String> entityNotFoundExceptionHandler(EntityNotFoundException err){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(err.getMessage());
    }
}
