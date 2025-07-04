package com.example.basicsamplesite.application.menu.dto;

import com.example.basicsamplesite.domain.menu.entity.Menu;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 메뉴 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class MenuApplicationDto {
    
    private final Long id;
    private final String menuName;
    private final String menuPath;
    private final String description;
    private final Boolean isActive;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    
    public MenuApplicationDto(Long id, String menuName, String menuPath, String description,
                             Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public static MenuApplicationDto from(Menu menu) {
        return new MenuApplicationDto(
            menu.getId(),
            menu.getMenuName(),
            menu.getMenuPath(),
            menu.getDescription(),
            menu.getIsActive(),
            menu.getCreatedAt(),
            menu.getUpdatedAt()
        );
    }
}
