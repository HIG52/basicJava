package com.example.basicsamplesite.domain.user.dto;

import com.example.basicsamplesite.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 회원 요약 정보 응답 DTO - Domain Layer
 */
@Getter
public class UserSummaryResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final User.UserRole role;
    private final LocalDate joinDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Boolean isActive;
    private final LocalDateTime lastLoginAt;
    
    public UserSummaryResponse(Long id, String name, String email, User.UserRole role, 
                              LocalDate joinDate, LocalDateTime createdAt, LocalDateTime updatedAt,
                              Boolean isActive, LocalDateTime lastLoginAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.joinDate = joinDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.lastLoginAt = lastLoginAt;
    }
} 