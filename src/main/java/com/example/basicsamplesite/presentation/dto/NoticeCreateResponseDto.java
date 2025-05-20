package com.example.basicsamplesite.presentation.dto;

import com.example.basicsamplesite.domain.dto.NoticeCreateResponse;

/**
 * 공지사항 생성 결과를 클라이언트에 전달하기 위한 DTO
 */
public record NoticeCreateResponseDto(
        Long id,
        String title,
        String message,
        boolean success
) {
    /**
     * 도메인 계층의 DTO를 presentation 계층의 DTO로 변환
     */
    public static NoticeCreateResponseDto from(NoticeCreateResponse domain) {
        return new NoticeCreateResponseDto(
                domain.id(),
                domain.title(),
                domain.message(),
                domain.id() != null
        );
    }
}
