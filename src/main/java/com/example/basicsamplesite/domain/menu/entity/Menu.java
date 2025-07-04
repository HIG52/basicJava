package com.example.basicsamplesite.domain.menu.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "menus")
public class Menu {

    // Getter 메소드들
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_SEQ")
    @SequenceGenerator(name = "MENU_SEQ", sequenceName = "MENU_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(nullable = false, length = 100, name = "menu_name")
    private String menuName;
    
    @Column(nullable = false, length = 200, name = "menu_path")
    private String menuPath;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false, name = "is_active")
    @org.hibernate.annotations.Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 기본 생성자
    protected Menu() {}
    
    // 생성자
    public Menu(String menuName, String menuPath, String description) {
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.description = description;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // 메뉴 수정을 위한 메소드
    public void updateMenu(String menuName, String menuPath, String description) {
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    // 메뉴 비활성화
    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }
    
    // 메뉴 활성화
    public void activate() {
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }

}
