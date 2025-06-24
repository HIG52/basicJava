package com.example.basicsamplesite.infrastructure.common.util;

import com.example.basicsamplesite.infrastructure.common.dto.PageRequest;
import com.example.basicsamplesite.infrastructure.common.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 페이지네이션 관련 유틸리티 클래스
 */
public class PageUtils {
    
    /**
     * PageRequest를 Spring Pageable로 변환
     */
    public static Pageable toPageable(PageRequest pageRequest) {
        return org.springframework.data.domain.PageRequest.of(
            pageRequest.getPage() - 1, // Spring은 0-based, 우리는 1-based
            pageRequest.getSize(),
            Sort.unsorted()
        );
    }
    
    /**
     * Spring Page를 PageResponse로 변환
     */
    public static <T, R> PageResponse<R> toPageResponse(Page<T> page, Function<T, R> mapper) {
        List<R> content = page.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());
        
        return PageResponse.of(
            content,
            PageRequest.of(page.getNumber() + 1, page.getSize()), // Spring은 0-based, 우리는 1-based
            (int) page.getTotalElements()
        );
    }
    
    /**
     * 전체 리스트에서 페이지에 해당하는 데이터만 추출
     */
    public static <T> List<T> getPageContent(List<T> allData, PageRequest pageRequest) {
        if (allData == null || allData.isEmpty()) {
            return Collections.emptyList();
        }
        
        int offset = pageRequest.getOffset();
        int size = pageRequest.getSize();
        int totalSize = allData.size();
        
        if (offset >= totalSize) {
            return Collections.emptyList();
        }
        
        int endIndex = Math.min(offset + size, totalSize);
        return allData.subList(offset, endIndex);
    }
    
    /**
     * 페이지 응답 생성 (전체 데이터를 받아서 페이징 처리)
     */
    public static <T> PageResponse<T> createPageResponse(List<T> allData, PageRequest pageRequest) {
        if (allData == null || allData.isEmpty()) {
            return PageResponse.empty(pageRequest);
        }
        
        List<T> pageContent = getPageContent(allData, pageRequest);
        return PageResponse.of(pageContent, pageRequest, allData.size());
    }
    
    /**
     * 페이지 응답 생성 (이미 페이징된 데이터와 전체 개수를 받음)
     */
    public static <T> PageResponse<T> createPageResponse(List<T> pageContent, PageRequest pageRequest, int totalElements) {
        return PageResponse.of(pageContent, pageRequest, totalElements);
    }
    
    /**
     * 전체 페이지 수 계산
     */
    public static int calculateTotalPages(int totalElements, int pageSize) {
        if (totalElements == 0) {
            return 0;
        }
        return (int) Math.ceil((double) totalElements / pageSize);
    }
    
    /**
     * 유효한 페이지 번호인지 확인
     */
    public static boolean isValidPage(int page, int totalPages) {
        return page >= 1 && page <= Math.max(1, totalPages);
    }
}
