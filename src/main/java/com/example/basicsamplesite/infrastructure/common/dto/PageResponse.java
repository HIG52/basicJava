package com.example.basicsamplesite.infrastructure.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * 페이지네이션 응답 정보를 담는 공통 DTO
 */
@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private final List<T> content;
    private final int currentPage;
    private final int pageSize;
    private final int totalElements;
    private final int totalPages;
    private final boolean first;
    private final boolean last;
    private final boolean hasNext;
    private final boolean hasPrevious;

    /**
     * 페이지 응답 생성
     */
    public static <T> PageResponse<T> of(List<T> content, PageRequest pageRequest, int totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getSize());
        int currentPage = pageRequest.getPage();
        
        return new PageResponse<>(
                content,
                currentPage,
                pageRequest.getSize(),
                totalElements,
                totalPages,
                currentPage == 1,
                currentPage == totalPages,
                currentPage < totalPages,
                currentPage > 1
        );
    }
    
    /**
     * 빈 페이지 응답 생성
     */
    public static <T> PageResponse<T> empty(PageRequest pageRequest) {
        return new PageResponse<>(
                Collections.emptyList(),
                pageRequest.getPage(),
                pageRequest.getSize(),
                0,
                0,
                true,
                true,
                false,
                false
        );
    }
    
    // 테스트에서 사용하는 메서드들
    public List<T> content() {
        return this.content;
    }
    
    public int currentPage() {
        return this.currentPage;
    }
    
    public int pageSize() {
        return this.pageSize;
    }
    
    public int totalElements() {
        return this.totalElements;
    }
    
    public int totalPages() {
        return this.totalPages;
    }
    
    public boolean first() {
        return this.first;
    }
    
    public boolean last() {
        return this.last;
    }
    
    public boolean hasNext() {
        return this.hasNext;
    }
    
    public boolean hasPrevious() {
        return this.hasPrevious;
    }
}
