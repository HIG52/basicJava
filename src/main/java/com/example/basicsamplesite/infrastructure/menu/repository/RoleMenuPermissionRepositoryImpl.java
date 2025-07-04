package com.example.basicsamplesite.infrastructure.menu.repository;

import com.example.basicsamplesite.domain.menu.entity.Menu;
import com.example.basicsamplesite.domain.menu.entity.RoleMenuPermission;
import com.example.basicsamplesite.domain.menu.repository.RoleMenuPermissionRepository;
import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import com.example.basicsamplesite.infrastructure.menu.jpa.RoleMenuPermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleMenuPermissionRepositoryImpl implements RoleMenuPermissionRepository {
    
    private final RoleMenuPermissionJpaRepository roleMenuPermissionJpaRepository;
    
    @Override
    public RoleMenuPermission save(RoleMenuPermission roleMenuPermission) {
        return roleMenuPermissionJpaRepository.save(roleMenuPermission);
    }
    
    @Override
    public boolean hasRoleMenuAccess(UserRole role, Long menuId) {
        return roleMenuPermissionJpaRepository.existsByRoleAndMenuId(role, menuId);
    }
    
    @Override
    public List<Menu> findAccessibleMenusByRole(UserRole role) {
        return roleMenuPermissionJpaRepository.findAccessibleMenusByRole(role);
    }
    
    @Override
    public List<RoleMenuPermission> findByRole(UserRole role) {
        return roleMenuPermissionJpaRepository.findByRole(role);
    }
    
    @Override
    public void deleteByRoleAndMenuId(UserRole role, Long menuId) {
        roleMenuPermissionJpaRepository.deleteByRoleAndMenuId(role, menuId);
    }
    
    @Override
    public boolean existsByRoleAndMenuId(UserRole role, Long menuId) {
        return roleMenuPermissionJpaRepository.existsByRoleAndMenuId(role, menuId);
    }
    
    @Override
    public List<UserRole> findRolesByMenuId(Long menuId) {
        return roleMenuPermissionJpaRepository.findRolesByMenuId(menuId);
    }
}
