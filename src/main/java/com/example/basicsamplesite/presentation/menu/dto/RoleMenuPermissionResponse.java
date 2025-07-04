package com.example.basicsamplesite.presentation.menu.dto;

import com.example.basicsamplesite.application.menu.dto.MenuApplicationDto;
import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 역할 기반 메뉴 권한 응답 DTO - Presentation Layer
 */
@Getter
public class RoleMenuPermissionResponse {
    
    private final UserRole role;
    private final List<MenuInfo> accessibleMenus;
    private final int totalMenuCount;
    
    public RoleMenuPermissionResponse(UserRole role, List<MenuApplicationDto> accessibleMenuDtos) {
        this.role = role;
        this.accessibleMenus = accessibleMenuDtos.stream()
                .map(MenuInfo::new)
                .collect(Collectors.toList());
        this.totalMenuCount = accessibleMenuDtos.size();
    }
    
    @Getter
    public static class MenuInfo {
        private final Long menuId;
        private final String menuName;
        private final String menuPath;
        private final String description;
        private final Boolean isActive;
        
        public MenuInfo(MenuApplicationDto menuDto) {
            this.menuId = menuDto.getId();
            this.menuName = menuDto.getMenuName();
            this.menuPath = menuDto.getMenuPath();
            this.description = menuDto.getDescription();
            this.isActive = menuDto.getIsActive();
        }
    }
}
