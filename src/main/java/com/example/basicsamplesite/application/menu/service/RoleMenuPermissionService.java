package com.example.basicsamplesite.application.menu.service;

import com.example.basicsamplesite.application.menu.dto.MenuApplicationDto;
import com.example.basicsamplesite.application.menu.dto.RoleMenuPermissionApplicationDto;
import com.example.basicsamplesite.domain.menu.entity.Menu;
import com.example.basicsamplesite.domain.menu.entity.RoleMenuPermission;
import com.example.basicsamplesite.domain.menu.repository.MenuRepository;
import com.example.basicsamplesite.domain.menu.repository.RoleMenuPermissionRepository;
import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import com.example.basicsamplesite.infrastructure.exception.menu.MenuNotFoundException;
import com.example.basicsamplesite.infrastructure.exception.menu.MenuPermissionAlreadyExistsException;
import com.example.basicsamplesite.infrastructure.exception.menu.MenuPermissionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleMenuPermissionService {
    
    private final MenuRepository menuRepository;
    private final RoleMenuPermissionRepository roleMenuPermissionRepository;
    
    /**
     * 해당 경로가 메뉴로 등록되어 있는지 확인
     * @param menuPath 메뉴 경로
     * @return true: 등록된 메뉴, false: 미등록 메뉴
     */
    public boolean isRegisteredMenu(String menuPath) {
        return menuRepository.findByMenuPath(menuPath).isPresent();
    }
    
    /**
     * 사용자 역할 기반 메뉴 접근 차단 여부 확인 (블랙리스트 방식)
     * @param userRole 사용자 역할
     * @param menuPath 메뉴 경로
     * @return true: 접근 차단됨, false: 접근 허용됨
     */
    public boolean isMenuBlocked(UserRole userRole, String menuPath) {
        // 메뉴가 존재하지 않으면 접근 허용 (블랙리스트 방식)
        Menu menu = menuRepository.findByMenuPath(menuPath).orElse(null);
        if (menu == null) {
            return false; // 메뉴가 없으면 모든 Role 접근 가능
        }
        
        // 메뉴가 존재하고 해당 Role에 대한 차단 권한이 있으면 접근 차단
        return roleMenuPermissionRepository.hasRoleMenuAccess(userRole, menu.getId());
    }
    
    /**
     * 역할별 접근 가능한 메뉴 조회 (블랙리스트 방식)
     * 차단되지 않은 모든 메뉴를 반환
     */
    public List<MenuApplicationDto> getRoleAccessibleMenus(UserRole userRole) {
        List<Menu> allMenus = menuRepository.findAll();
        List<Long> blockedMenuIds = roleMenuPermissionRepository.findByRole(userRole).stream()
                .map(RoleMenuPermission::getMenuId)
                .collect(Collectors.toList());
        
        return allMenus.stream()
                .filter(menu -> !blockedMenuIds.contains(menu.getId()))
                .map(MenuApplicationDto::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 역할에 메뉴 접근 차단 권한 부여 (블랙리스트 추가)
     */
    @Transactional
    public RoleMenuPermissionApplicationDto blockRoleMenuAccess(UserRole role, Long menuId, String createdBy) {
        // 이미 차단되어 있는지 확인
        if (roleMenuPermissionRepository.existsByRoleAndMenuId(role, menuId)) {
            throw new MenuPermissionAlreadyExistsException("해당 역할에 이미 메뉴 접근 차단 권한이 있습니다.");
        }
        
        // 메뉴 존재 확인
        menuRepository.findById(menuId)
            .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."));
        
        RoleMenuPermission roleMenuPermission = new RoleMenuPermission(role, menuId, createdBy);
        RoleMenuPermission savedRoleMenuPermission = roleMenuPermissionRepository.save(roleMenuPermission);
        
        return RoleMenuPermissionApplicationDto.from(savedRoleMenuPermission);
    }
    
    /**
     * 역할의 메뉴 접근 차단 해제 (블랙리스트에서 제거)
     */
    @Transactional
    public void allowRoleMenuAccess(UserRole role, Long menuId) {
        if (!roleMenuPermissionRepository.existsByRoleAndMenuId(role, menuId)) {
            throw new MenuPermissionNotFoundException("해당 역할에 메뉴 접근 차단 권한이 없습니다.");
        }
        
        roleMenuPermissionRepository.deleteByRoleAndMenuId(role, menuId);
    }
    
    /**
     * 역할별 메뉴 차단 권한 목록 조회 (블랙리스트 목록)
     */
    public List<RoleMenuPermissionApplicationDto> getRoleMenuBlocks(UserRole role) {
        return roleMenuPermissionRepository.findByRole(role).stream()
                .map(RoleMenuPermissionApplicationDto::from)
                .collect(Collectors.toList());
    }
    
    /**
     * 특정 메뉴에 접근이 차단된 역할들 조회 (블랙리스트에 있는 역할들)
     */
    public List<UserRole> getMenuBlockedRoles(Long menuId) {
        return roleMenuPermissionRepository.findRolesByMenuId(menuId);
    }
}
