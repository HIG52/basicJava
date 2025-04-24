package com.example.basicsamplesite.domain.dto;

import com.example.basicsamplesite.domain.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoticeCreateRequest(
        @NotBlank(message = "제목은 필수 입력값입니다")
        @Size(min = 2, max = 100, message = "제목은 2자 이상 100자 이하로 입력해주세요")
        String title,

        @NotBlank(message = "내용은 필수 입력값입니다")
        String content,

        @NotBlank(message = "카테고리는 필수 입력값입니다")
        String category
) {
    public Notice toEntity() {
        return new Notice(title, content, category);
    }
}
