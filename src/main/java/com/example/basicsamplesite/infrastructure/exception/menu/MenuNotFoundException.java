package com.example.basicsamplesite.infrastructure.exception.menu;

public class MenuNotFoundException extends RuntimeException {
    
    public MenuNotFoundException(String message) {
        super(message);
    }
    
    public MenuNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
