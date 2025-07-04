package com.example.basicsamplesite.application.menu.command;

public class CreateMenuCommand {
    
    private final String menuName;
    private final String menuPath;
    private final String description;
    
    public CreateMenuCommand(String menuName, String menuPath, String description) {
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.description = description;
    }
    
    public String getMenuName() {
        return menuName;
    }
    
    public String getMenuPath() {
        return menuPath;
    }
    
    public String getDescription() {
        return description;
    }
}
