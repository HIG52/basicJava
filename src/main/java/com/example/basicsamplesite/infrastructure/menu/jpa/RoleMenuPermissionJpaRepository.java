package com.example.basicsamplesite.infrastructure.menu.jpa;

import com.example.basicsamplesite.domain.menu.entity.RoleMenuPermission;
import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleMenuPermissionJpaRepository extends JpaRepository<RoleMenuPermission, Long> {
    
    boolean existsByRoleAndMenuId(UserRole role, Long menuId);
    
    @Query("SELECT m FROM Menu m JOIN RoleMenuPermission rmp ON m.id = rmp.menuId WHERE rmp.role = :role AND m.isActive = true ORDER BY m.menuName")
    List<com.example.basicsamplesite.domain.menu.entity.Menu> findAccessibleMenusByRole(@Param("role") UserRole role);
    
    List<RoleMenuPermission> findByRole(UserRole role);
    
    void deleteByRoleAndMenuId(UserRole role, Long menuId);
    
    @Query("SELECT rmp.role FROM RoleMenuPermission rmp WHERE rmp.menuId = :menuId")
    List<UserRole> findRolesByMenuId(@Param("menuId") Long menuId);
}
