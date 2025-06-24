package com.example.basicsamplesite.application.qna.dto;

import lombok.Getter;

import java.util.List;

/**
 * QnA 목록 Application DTO - Domain Layer에서 Application Layer로 전달되는 DTO
 */
@Getter
public class QnaListApplicationDto {
    private final List<QnaApplicationDto> list;
    private final long totalItems;
    private final int currentPage;
    private final int itemsPerPage;
    private final int totalPages;

    public QnaListApplicationDto(List<QnaApplicationDto> list, long totalItems, 
                                int currentPage, int itemsPerPage, int totalPages) {
        this.list = list;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
    }

}
