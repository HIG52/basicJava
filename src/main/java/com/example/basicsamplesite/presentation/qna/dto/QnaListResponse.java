package com.example.basicsamplesite.presentation.qna.dto;

import lombok.Getter;

import java.util.List;

/**
 * QnA 목록 응답 DTO - Presentation Layer
 */
@Getter
public class QnaListResponse {
    private List<QnaResponse> list;
    private long totalItems;
    private int currentPage;
    private int itemsPerPage;
    private int totalPages;
    
    public QnaListResponse(List<QnaResponse> list, long totalItems, 
                          int currentPage, int itemsPerPage, int totalPages) {
        this.list = list;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalPages;
    }

}
