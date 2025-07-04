package com.example.basicsamplesite.infrastructure.exception.menu;

public class MenuPermissionAlreadyExistsException extends RuntimeException {
    
    public MenuPermissionAlreadyExistsException(String message) {
        super(message);
    }
    
    public MenuPermissionAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
