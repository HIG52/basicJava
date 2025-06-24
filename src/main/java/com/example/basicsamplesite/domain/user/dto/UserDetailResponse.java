package com.example.basicsamplesite.domain.user.dto;

import com.example.basicsamplesite.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 회원 상세 정보 응답 DTO - Domain Layer
 */
@Getter
public class UserDetailResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final User.UserRole role;
    private final LocalDate joinDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime lastLoginAt;
    private final Boolean isActive;
    private final String phone;
    private final String address;
    private final String zipcode;
    private final String addressDetail;
    private final LocalDate birthDate;
    private final Object statistics; // 회원 통계 정보 (추후 확장용)
    
    public UserDetailResponse(Long id, String name, String email, User.UserRole role,
                             LocalDate joinDate, LocalDateTime createdAt, LocalDateTime updatedAt,
                             LocalDateTime lastLoginAt, Boolean isActive,
                             String phone, String address, String zipcode, String addressDetail,
                             LocalDate birthDate, Object statistics) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.joinDate = joinDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLoginAt = lastLoginAt;
        this.isActive = isActive;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
        this.addressDetail = addressDetail;
        this.birthDate = birthDate;
        this.statistics = statistics;
    }
} 