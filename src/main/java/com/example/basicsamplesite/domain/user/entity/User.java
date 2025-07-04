package com.example.basicsamplesite.domain.user.entity;

import com.example.basicsamplesite.domain.common.entity.BaseEntity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 회원 엔티티
 */
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    @SequenceGenerator(name = "USER_SEQ_GENERATOR", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(nullable = false, length = 20, unique = true, name = "user_id")
    private String userId;
    
    @Column(nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserRole role = UserRole.USER;
    
    @Column(nullable = false, columnDefinition = "NUMBER(1) DEFAULT 1", name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
    
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    private String phone;
    
    private String address;
    
    private String zipcode;
    
    @Column(name = "address_detail")
    private String addressDetail;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;

    public enum UserRole {
        ADMIN, USER
    }

    public void updateUser(String userId, String username, String email, UserRole role, String phone, Boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.phone = phone;
        this.isActive = isActive;
    }

    public void updateUserWithAddress(String userId, String username, String email, UserRole role, String phone, 
                                    String address, String zipcode, String addressDetail, 
                                    LocalDate birthDate, Boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
        this.addressDetail = addressDetail;
        this.birthDate = birthDate;
        this.isActive = isActive;
    }

    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void updateActiveStatus(Boolean isActive) {
        this.isActive = isActive;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean isActive() {
        return this.isActive;
    }
}
