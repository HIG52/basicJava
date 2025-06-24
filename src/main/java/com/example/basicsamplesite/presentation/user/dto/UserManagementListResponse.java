package com.example.basicsamplesite.presentation.user.dto;

import java.util.List;

/**
 * 회원 관리 목록 응답 DTO - Presentation Layer
 */
public class UserManagementListResponse {
    private List<UserManagementResponse> list;
    private long totalItems;
    private int currentPage;
    private int itemsPerPage;
    private int totalPages;
    
    public UserManagementListResponse(List<UserManagementResponse> list, long totalItems, 
                                     int currentPage, int itemsPerPage, int totalPages) {
        this.list = list;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
    }
    
    public List<UserManagementResponse> getList() {
        return list;
    }
    
    public long getTotalItems() {
        return totalItems;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    public int getItemsPerPage() {
        return itemsPerPage;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
}
