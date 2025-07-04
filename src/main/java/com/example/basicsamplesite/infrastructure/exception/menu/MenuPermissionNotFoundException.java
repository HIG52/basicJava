package com.example.basicsamplesite.infrastructure.exception.menu;

public class MenuPermissionNotFoundException extends RuntimeException {
    
    public MenuPermissionNotFoundException(String message) {
        super(message);
    }
    
    public MenuPermissionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
