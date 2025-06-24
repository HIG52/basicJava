package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

/**
 * 로그인 응답 DTO - Presentation Layer
 */
@Getter
public class LoginResponse {
    private String token;
    private UserInfo user;
    
    public LoginResponse(String token, UserInfo user) {
        this.token = token;
        this.user = user;
    }

    /**
     * 사용자 정보 내부 클래스
     */
    public static class UserInfo {
        private Long id;
        private String name;
        private String email;
        private String role;
        
        public UserInfo(Long id, String name, String email, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
        }
        
        public Long getId() {
            return id;
        }
        
        public String getName() {
            return name;
        }
        
        public String getEmail() {
            return email;
        }
        
        public String getRole() {
            return role;
        }
    }
}
