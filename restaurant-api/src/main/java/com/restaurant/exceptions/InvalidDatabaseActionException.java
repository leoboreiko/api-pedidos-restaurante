package com.restaurant.exceptions;

public class InvalidDatabaseActionException extends RuntimeException {
    
    public InvalidDatabaseActionException(String message){
        super(message);
    }
}
