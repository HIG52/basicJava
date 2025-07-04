package com.example.basicsamplesite.infrastructure.exception.menu;

public class MenuAccessDeniedException extends RuntimeException {
    
    public MenuAccessDeniedException(String message) {
        super(message);
    }
    
    public MenuAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
