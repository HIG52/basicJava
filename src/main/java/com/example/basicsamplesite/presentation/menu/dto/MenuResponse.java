package com.example.basicsamplesite.presentation.menu.dto;

import com.example.basicsamplesite.application.menu.dto.MenuApplicationDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/**
 * 메뉴 응답 DTO - Presentation Layer
 */
@Getter
public class MenuResponse {
    
    private final Long menuId;
    private final String menuName;
    private final String menuPath;
    private final String description;
    private final Boolean isActive;
    private final String createdAt;
    private final String updatedAt;
    
    public MenuResponse(MenuApplicationDto menuDto) {
        this.menuId = menuDto.getId();
        this.menuName = menuDto.getMenuName();
        this.menuPath = menuDto.getMenuPath();
        this.description = menuDto.getDescription();
        this.isActive = menuDto.getIsActive();
        this.createdAt = menuDto.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updatedAt = menuDto.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
