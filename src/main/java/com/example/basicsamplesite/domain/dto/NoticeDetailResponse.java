package com.example.basicsamplesite.domain.dto;

import com.example.basicsamplesite.domain.entity.Notice;

import java.time.LocalDateTime;

/**
 * 공지사항 상세 정보를 도메인 계층 내에서 전달하기 위한 DTO
 */
public record NoticeDetailResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        Integer viewCount
) {
    /**
     * 엔티티에서 도메인 DTO로 변환
     */
    public static NoticeDetailResponse from(Notice notice) {
        return new NoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCreatedAt(),
                notice.getViewCount()
        );
    }
}
