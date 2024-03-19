package com.numbank.app.exception;

public class DatabaseConnectionException extends RuntimeException {
    
    public DatabaseConnectionException(String message) {
        super((message));
    }
}
