package com.example.basicsamplesite.application.notice.dto;

import lombok.Getter;

import java.util.List;

/**
 * 공지사항 목록 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class NoticeListApplicationDto {
    private final List<NoticeApplicationDto> notices;
    private final long totalItems;
    private final int currentPage;
    private final int itemsPerPage;
    private final int totalPages;

    public NoticeListApplicationDto(List<NoticeApplicationDto> notices, long totalItems, 
                                   int currentPage, int itemsPerPage, int totalPages) {
        this.notices = notices;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
    }

}
