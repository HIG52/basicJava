package com.example.basicsamplesite.domain.dto;

import com.example.basicsamplesite.domain.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 공지사항 목록 요약 정보를 도메인 계층 내에서 전달하기 위한 DTO
 */
public record NoticeSummaryResponse(
        Long id,
        String title,
        LocalDateTime createdAt,
        Integer viewCount
) {
    /**
     * 엔티티에서 도메인 DTO로 변환
     */
    public static NoticeSummaryResponse from(Notice notice) {
        return new NoticeSummaryResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getCreatedAt(),
                notice.getViewCount()
        );
    }
    
    /**
     * 엔티티 목록에서 도메인 DTO 목록으로 변환
     */
    public static List<NoticeSummaryResponse> listFrom(List<Notice> notices) {
        return notices.stream()
                .map(NoticeSummaryResponse::from)
                .collect(Collectors.toList());
    }
}
