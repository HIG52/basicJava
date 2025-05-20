package com.example.basicsamplesite.presentation.dto;

import com.example.basicsamplesite.domain.dto.NoticeSummaryResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 공지사항 목록 요약 정보를 클라이언트에 전달하기 위한 DTO
 */
public record NoticeSummaryDto(
        Long id,
        String title,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        int viewCount
) {
    /**
     * 도메인 계층의 DTO를 presentation 계층의 DTO로 변환
     */
    public static NoticeSummaryDto from(NoticeSummaryResponse domain) {
        return new NoticeSummaryDto(
                domain.id(),
                domain.title(),
                domain.createdAt(),
                domain.viewCount()
        );
    }
    
    /**
     * 도메인 계층의 DTO 목록을 presentation 계층의 DTO 목록으로 변환
     */
    public static List<NoticeSummaryDto> listFrom(List<NoticeSummaryResponse> domains) {
        return domains.stream()
                .map(NoticeSummaryDto::from)
                .collect(Collectors.toList());
    }
}
