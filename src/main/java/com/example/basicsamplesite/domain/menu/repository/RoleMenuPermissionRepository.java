package com.example.basicsamplesite.domain.menu.repository;

import com.example.basicsamplesite.domain.menu.entity.Menu;
import com.example.basicsamplesite.domain.menu.entity.RoleMenuPermission;
import com.example.basicsamplesite.domain.user.entity.User.UserRole;

import java.util.List;

public interface RoleMenuPermissionRepository {
    
    RoleMenuPermission save(RoleMenuPermission roleMenuPermission);
    
    boolean hasRoleMenuAccess(UserRole role, Long menuId);
    
    List<Menu> findAccessibleMenusByRole(UserRole role);
    
    List<RoleMenuPermission> findByRole(UserRole role);
    
    void deleteByRoleAndMenuId(UserRole role, Long menuId);
    
    boolean existsByRoleAndMenuId(UserRole role, Long menuId);
    
    List<UserRole> findRolesByMenuId(Long menuId);
}
