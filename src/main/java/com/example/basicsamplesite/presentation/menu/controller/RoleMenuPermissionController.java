package com.example.basicsamplesite.presentation.menu.controller;

import com.example.basicsamplesite.application.menu.dto.MenuApplicationDto;
import com.example.basicsamplesite.application.menu.service.RoleMenuPermissionService;
import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import com.example.basicsamplesite.infrastructure.config.UserDetailsImpl;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import com.example.basicsamplesite.presentation.menu.dto.RoleMenuPermissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일반 사용자 역할 메뉴 권한 관련 Controller (조회 전용)
 */
@RestController
@RequestMapping("/role-menu-permissions")
@RequiredArgsConstructor
public class RoleMenuPermissionController {
    
    private final RoleMenuPermissionService roleMenuPermissionService;
    
    /**
     * 내 역할의 접근 가능한 메뉴 조회
     */
    @GetMapping("/my-menus")
    public ResponseEntity<ApiResponse<RoleMenuPermissionResponse>> getMyAccessibleMenus() {
        
        // 스프링 시큐리티에서 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("로그인이 필요합니다."));
        }
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserRole userRole = userDetails.getUser().getRole();
        
        List<MenuApplicationDto> accessibleMenuDtos = roleMenuPermissionService.getRoleAccessibleMenus(userRole);
        RoleMenuPermissionResponse response = new RoleMenuPermissionResponse(userRole, accessibleMenuDtos);
        
        return ResponseEntity.ok(ApiResponse.success("내 역할의 메뉴 권한을 성공적으로 조회했습니다.", response));
    }
    
    /**
     * 메뉴 접근 권한 확인
     */
    @GetMapping("/check-access")
    public ResponseEntity<ApiResponse<Boolean>> checkMenuAccess(@RequestParam String menuPath) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("로그인이 필요합니다."));
        }
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserRole userRole = userDetails.getUser().getRole();
        
        boolean hasAccess = !roleMenuPermissionService.isMenuBlocked(userRole, menuPath);
        
        return ResponseEntity.ok(ApiResponse.success("메뉴 접근 권한을 확인했습니다.", hasAccess));
    }
}
