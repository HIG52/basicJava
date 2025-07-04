package com.example.basicsamplesite.application.menu.command;

public class UpdateMenuCommand {
    
    private final Long menuId;
    private final String menuName;
    private final String menuPath;
    private final String description;
    
    public UpdateMenuCommand(Long menuId, String menuName, String menuPath, String description) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.description = description;
    }
    
    public Long getMenuId() {
        return menuId;
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
