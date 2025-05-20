package com.example.basicsamplesite.presentation.dto;

import com.example.basicsamplesite.domain.dto.NoticeCreateRequest;
import com.example.basicsamplesite.domain.dto.NoticeCreateResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 공지사항 생성 요청을 클라이언트로부터 받기 위한 DTO
 */
public record NoticeCreateDto(
        @NotBlank(message = "제목은 필수 입력값입니다")
        @Size(min = 2, max = 100, message = "제목은 2자 이상 100자 이하로 입력해주세요")
        String title,

        @NotBlank(message = "내용은 필수 입력값입니다")
        String content
) {
    /**
     * presentation 계층의 DTO를 domain 계층의 DTO로 변환
     */
    public NoticeCreateRequest toDomain() {
        return new NoticeCreateRequest(title, content);
    }
}
