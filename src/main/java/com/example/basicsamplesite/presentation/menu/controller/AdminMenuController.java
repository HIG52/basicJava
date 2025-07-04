package com.example.basicsamplesite.presentation.menu.controller;

import com.example.basicsamplesite.application.menu.command.CreateMenuCommand;
import com.example.basicsamplesite.application.menu.command.UpdateMenuCommand;
import com.example.basicsamplesite.application.menu.dto.MenuApplicationDto;
import com.example.basicsamplesite.application.menu.service.MenuService;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import com.example.basicsamplesite.presentation.menu.dto.MenuListResponse;
import com.example.basicsamplesite.presentation.menu.dto.MenuRequest;
import com.example.basicsamplesite.presentation.menu.dto.MenuResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관리자 메뉴 관련 Controller
 */
@RestController
@RequestMapping("/admin/menus")
@RequiredArgsConstructor
public class AdminMenuController {
    
    private final MenuService menuService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<MenuResponse>> createMenu(@Valid @RequestBody MenuRequest request) {
        CreateMenuCommand command = new CreateMenuCommand(
                request.getMenuName(),
                request.getMenuPath(),
                request.getDescription()
        );
        
        MenuApplicationDto menuDto = menuService.createMenu(command.getMenuName(), command.getMenuPath(), command.getDescription());
        MenuResponse response = new MenuResponse(menuDto);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("메뉴가 성공적으로 생성되었습니다.", response));
    }
    
    @PutMapping("/{menuId}")
    public ResponseEntity<ApiResponse<MenuResponse>> updateMenu(
            @PathVariable Long menuId,
            @Valid @RequestBody MenuRequest request) {
        
        UpdateMenuCommand command = new UpdateMenuCommand(
                menuId,
                request.getMenuName(),
                request.getMenuPath(),
                request.getDescription()
        );
        
        MenuApplicationDto menuDto = menuService.updateMenu(command.getMenuId(), command.getMenuName(), 
                command.getMenuPath(), command.getDescription());
        MenuResponse response = new MenuResponse(menuDto);
        
        return ResponseEntity.ok(ApiResponse.success("메뉴가 성공적으로 수정되었습니다.", response));
    }
    
    @PatchMapping("/{menuId}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateMenu(@PathVariable Long menuId) {
        menuService.deactivateMenu(menuId);
        return ResponseEntity.ok(ApiResponse.success("메뉴가 비활성화되었습니다.", null));
    }
    
    @PatchMapping("/{menuId}/activate")
    public ResponseEntity<ApiResponse<Void>> activateMenu(@PathVariable Long menuId) {
        menuService.activateMenu(menuId);
        return ResponseEntity.ok(ApiResponse.success("메뉴가 활성화되었습니다.", null));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<MenuListResponse>> getAllMenus() {
        List<MenuApplicationDto> menuDtos = menuService.getAllMenusForAdmin();
        MenuListResponse response = new MenuListResponse(menuDtos);
        
        return ResponseEntity.ok(ApiResponse.success("메뉴 목록을 성공적으로 조회했습니다.", response));
    }
    
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
