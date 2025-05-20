package com.example.basicsamplesite.presentation.dto;

import com.example.basicsamplesite.domain.dto.NoticeDetailResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 공지사항 상세 정보를 클라이언트에 전달하기 위한 DTO
 */
public record NoticeDetailDto(
        Long id,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        int viewCount
) {
    /**
     * 도메인 계층의 DTO를 presentation 계층의 DTO로 변환
     */
    public static NoticeDetailDto from(NoticeDetailResponse domain) {
        return new NoticeDetailDto(
                domain.id(),
                domain.title(),
                domain.content(),
                domain.createdAt(),
                domain.viewCount()
        );
    }
}
