package com.example.basicsamplesite.application.menu.dto;

import com.example.basicsamplesite.domain.menu.entity.RoleMenuPermission;
import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 역할 기반 메뉴 권한 애플리케이션 DTO - Application Layer
 */
@Getter
public class RoleMenuPermissionApplicationDto {
    
    private final Long id;
    private final UserRole role;
    private final Long menuId;
    private final LocalDateTime createdAt;
    private final String createdBy;
    
    public RoleMenuPermissionApplicationDto(Long id, UserRole role, Long menuId, 
                                          LocalDateTime createdAt, String createdBy) {
        this.id = id;
        this.role = role;
        this.menuId = menuId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
    
    public static RoleMenuPermissionApplicationDto from(RoleMenuPermission roleMenuPermission) {
        return new RoleMenuPermissionApplicationDto(
                roleMenuPermission.getId(),
                roleMenuPermission.getRole(),
                roleMenuPermission.getMenuId(),
                roleMenuPermission.getCreatedAt(),
                roleMenuPermission.getCreatedBy()
        );
    }
}
