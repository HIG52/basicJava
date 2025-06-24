package com.example.basicsamplesite.infrastructure.common.dto;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 페이지네이션 요청 정보를 담는 DTO
 */
@Getter
@AllArgsConstructor
public class PageRequest {
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private final int page;
    
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    private final int size;

    /**
     * 기본 페이지 요청 생성 (1페이지, 10개)
     */
    public static PageRequest of() {
        return new PageRequest(1, 10);
    }
    
    /**
     * 페이지 요청 생성
     */
    public static PageRequest of(int page, int size) {
        return new PageRequest(
                Math.max(1, page),  // 최소 1페이지
                Math.max(1, Math.min(100, size))  // 최소 1개, 최대 100개
        );
    }
    
    /**
     * 0-based offset 계산
     */
    public int getOffset() {
        return (page - 1) * size;
    }
    
    // 테스트에서 사용하는 메서드들
    public int page() {
        return this.page;
    }
    
    public int size() {
        return this.size;
    }
}
