package com.example.basicsamplesite.presentation.menu.dto;

import com.example.basicsamplesite.application.menu.dto.MenuApplicationDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 메뉴 목록 응답 DTO - Presentation Layer
 */
@Getter
public class MenuListResponse {
    
    private final List<MenuResponse> menus;
    private final int totalCount;
    
    public MenuListResponse(List<MenuApplicationDto> menuDtos) {
        this.menus = menuDtos.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
        this.totalCount = menuDtos.size();
    }
}
