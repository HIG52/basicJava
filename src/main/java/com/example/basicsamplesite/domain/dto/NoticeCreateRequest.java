package com.example.basicsamplesite.domain.dto;

/**
 * 공지사항 생성을 위한 도메인 계층 DTO
 */
public record NoticeCreateRequest(
        String title,
        String content
) {
    /**
     * 유효성 검사는 presentation 계층에서 처리하고
     * 도메인 계층에서는 비즈니스 로직 처리에 필요한 정보만 포함
     */
}
