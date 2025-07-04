package com.example.basicsamplesite.presentation.menu.controller;

import com.example.basicsamplesite.application.menu.dto.MenuApplicationDto;
import com.example.basicsamplesite.application.menu.dto.RoleMenuPermissionApplicationDto;
import com.example.basicsamplesite.application.menu.service.RoleMenuPermissionService;
import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import com.example.basicsamplesite.infrastructure.config.UserDetailsImpl;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import com.example.basicsamplesite.presentation.menu.dto.RoleMenuPermissionRequest;
import com.example.basicsamplesite.presentation.menu.dto.RoleMenuPermissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 관리자 역할 메뉴 권한 관련 Controller
 */
@RestController
@RequestMapping("/admin/role-menu-permissions")
@RequiredArgsConstructor
public class AdminRoleMenuPermissionController {
    
    private final RoleMenuPermissionService roleMenuPermissionService;
    
    /**
     * 역할에 메뉴 접근 차단 권한 부여 (블랙리스트 추가)
     */
    @PostMapping("/block")
    public ResponseEntity<ApiResponse<RoleMenuPermissionApplicationDto>> blockRoleMenuAccess(
            @Valid @RequestBody RoleMenuPermissionRequest request) {
        
        // 스프링 시큐리티에서 현재 사용자 정보 가져오기 (관리자만 권한 부여 가능)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("로그인이 필요합니다."));
        }
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String currentUser = userDetails.getUsername();
        
        RoleMenuPermissionApplicationDto result = roleMenuPermissionService.blockRoleMenuAccess(
                request.getRole(),
                request.getMenuId(),
                currentUser
        );
        
        return ResponseEntity.ok(ApiResponse.success("역할 메뉴 접근 차단이 성공적으로 설정되었습니다.", result));
    }
    
    /**
     * 역할의 메뉴 접근 차단 해제 (블랙리스트에서 제거)
     */
    @DeleteMapping("/allow")
    public ResponseEntity<ApiResponse<Void>> allowRoleMenuAccess(
            @Valid @RequestBody RoleMenuPermissionRequest request) {
        
        roleMenuPermissionService.allowRoleMenuAccess(request.getRole(), request.getMenuId());
        
        return ResponseEntity.ok(ApiResponse.success("역할 메뉴 접근 차단이 성공적으로 해제되었습니다.", null));
    }
    
    /**
     * 특정 역할의 접근 가능한 메뉴 조회
     */
    @GetMapping("/roles/{role}")
    public ResponseEntity<ApiResponse<RoleMenuPermissionResponse>> getRoleAccessibleMenus(
            @PathVariable UserRole role) {
        
        List<MenuApplicationDto> accessibleMenuDtos = roleMenuPermissionService.getRoleAccessibleMenus(role);
        RoleMenuPermissionResponse response = new RoleMenuPermissionResponse(role, accessibleMenuDtos);
        
        return ResponseEntity.ok(ApiResponse.success("역할별 메뉴 권한을 성공적으로 조회했습니다.", response));
    }
    
    /**
     * 내 역할의 접근 가능한 메뉴 조회
     */
    @GetMapping("/my-menus")
    public ResponseEntity<ApiResponse<RoleMenuPermissionResponse>> getMyAccessibleMenus() {
        
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
    
    /**
     * 특정 메뉴에 접근이 차단된 역할들 조회 (블랙리스트)
     */
    @GetMapping("/menus/{menuId}/blocked-roles")
    public ResponseEntity<ApiResponse<List<UserRole>>> getMenuBlockedRoles(
            @PathVariable Long menuId) {
        
        List<UserRole> blockedRoles = roleMenuPermissionService.getMenuBlockedRoles(menuId);
        
        return ResponseEntity.ok(ApiResponse.success("메뉴 접근이 차단된 역할들을 성공적으로 조회했습니다.", blockedRoles));
    }
    
    /**
     * 모든 사용자 역할 조회
     */
    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<UserRole>>> getAllUserRoles() {
        List<UserRole> roles = Arrays.asList(UserRole.values());
        return ResponseEntity.ok(ApiResponse.success("모든 사용자 역할을 성공적으로 조회했습니다.", roles));
    }
}
