package com.example.basicsamplesite.domain.menu.entity;

import com.example.basicsamplesite.domain.user.entity.User.UserRole;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "role_menu_permissions")
public class RoleMenuPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_MENU_PERMISSION_SEQ")
    @SequenceGenerator(name = "ROLE_MENU_PERMISSION_SEQ", sequenceName = "ROLE_MENU_PERMISSION_SEQ", allocationSize = 1)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role;
    
    @Column(name = "menu_id", nullable = false)
    private Long menuId;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "created_by")
    private String createdBy;
    
    // 기본 생성자
    protected RoleMenuPermission() {}
    
    // 생성자
    public RoleMenuPermission(UserRole role, Long menuId) {
        this.role = role;
        this.menuId = menuId;
        this.createdAt = LocalDateTime.now();
    }
    
    // 생성자 (생성자 정보 포함)
    public RoleMenuPermission(UserRole role, Long menuId, String createdBy) {
        this.role = role;
        this.menuId = menuId;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    // 권한 정보 비교를 위한 메소드
    public boolean isSamePermission(UserRole role, Long menuId) {
        return this.role.equals(role) && this.menuId.equals(menuId);
    }
}
