package com.example.basicsamplesite.presentation.menu.dto;

import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 역할 기반 메뉴 권한 요청 DTO - Presentation Layer
 */
@Getter
@NoArgsConstructor
public class RoleMenuPermissionRequest {
    
    @NotNull(message = "역할은 필수입니다.")
    private UserRole role;
    
    @NotNull(message = "메뉴 ID는 필수입니다.")
    private Long menuId;
    
    public RoleMenuPermissionRequest(UserRole role, Long menuId) {
        this.role = role;
        this.menuId = menuId;
    }
}
