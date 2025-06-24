package com.example.basicsamplesite.presentation.user.dto;

/**
 * 회원 관리 응답 DTO - Presentation Layer
 */
public class UserManagementResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String joinDate;
    
    public UserManagementResponse(Long id, String name, String email, String role, String joinDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.joinDate = joinDate;
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
    
    public String getJoinDate() {
        return joinDate;
    }
}
