package com.numbank.app.exception;

public class BadValueException extends RuntimeException {

    public BadValueException(String message) {
        super((message));
    }
}
