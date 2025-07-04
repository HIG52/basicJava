package com.example.basicsamplesite.presentation.menu.controller;

import com.example.basicsamplesite.application.menu.dto.MenuApplicationDto;
import com.example.basicsamplesite.application.menu.service.MenuService;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import com.example.basicsamplesite.presentation.menu.dto.MenuListResponse;
import com.example.basicsamplesite.presentation.menu.dto.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일반 사용자 메뉴 관련 Controller (조회 전용)
 */
@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {
    
    private final MenuService menuService;
    
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<MenuListResponse>> getActiveMenus() {
        List<MenuApplicationDto> menuDtos = menuService.getAllActiveMenus();
        MenuListResponse response = new MenuListResponse(menuDtos);
        
        return ResponseEntity.ok(ApiResponse.success("활성 메뉴 목록을 성공적으로 조회했습니다.", response));
    }
    
    @GetMapping("/{menuId}")
    public ResponseEntity<ApiResponse<MenuResponse>> getMenu(@PathVariable Long menuId) {
        MenuApplicationDto menuDto = menuService.getMenuById(menuId);
        MenuResponse response = new MenuResponse(menuDto);
        
        return ResponseEntity.ok(ApiResponse.success("메뉴 정보를 성공적으로 조회했습니다.", response));
    }
}
