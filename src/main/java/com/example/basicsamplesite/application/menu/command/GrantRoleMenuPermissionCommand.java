package com.example.basicsamplesite.application.menu.command;

import com.example.basicsamplesite.domain.user.entity.User.UserRole;

public class GrantRoleMenuPermissionCommand {
    
    private final UserRole role;
    private final Long menuId;
    private final String createdBy;
    
    public GrantRoleMenuPermissionCommand(UserRole role, Long menuId, String createdBy) {
        this.role = role;
        this.menuId = menuId;
        this.createdBy = createdBy;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public Long getMenuId() {
        return menuId;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
}
