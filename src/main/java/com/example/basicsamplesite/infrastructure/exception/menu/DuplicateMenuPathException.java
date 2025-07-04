package com.example.basicsamplesite.infrastructure.exception.menu;

public class DuplicateMenuPathException extends RuntimeException {
    
    public DuplicateMenuPathException(String message) {
        super(message);
    }
    
    public DuplicateMenuPathException(String message, Throwable cause) {
        super(message, cause);
    }
}
