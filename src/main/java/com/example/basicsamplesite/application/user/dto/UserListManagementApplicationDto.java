package com.example.basicsamplesite.application.user.dto;

import lombok.Getter;

import java.util.List;

/**
 * 회원 목록 관리 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class UserListManagementApplicationDto {
    private final List<UserManagementApplicationDto> list;
    private final long totalItems;
    private final int currentPage;
    private final int itemsPerPage;
    private final int totalPages;

    public UserListManagementApplicationDto(List<UserManagementApplicationDto> list, long totalItems, 
                                           int currentPage, int itemsPerPage, int totalPages) {
        this.list = list;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
    }

}
