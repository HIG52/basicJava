package com.example.basicsamplesite.presentation.notice.dto;

import lombok.Getter;

import java.util.List;

/**
 * 공지사항 목록 응답 DTO - Presentation Layer
 */
@Getter
public class NoticeListResponse {
    private final List<NoticeResponse> notices;
    private final long totalItems;
    private final int currentPage;
    private final int itemsPerPage;
    private final int totalPages;
    
    public NoticeListResponse(List<NoticeResponse> notices, long totalItems, 
                             int currentPage, int itemsPerPage, int totalPages) {
        this.notices = notices;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
    }

}
