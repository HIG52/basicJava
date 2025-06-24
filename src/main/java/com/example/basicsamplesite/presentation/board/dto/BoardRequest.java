package com.example.basicsamplesite.presentation.board.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 게시글 작성/수정 요청 DTO - Presentation Layer
 */
@Getter
public class BoardRequest {
    
    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다")
    private String title;
    
    @NotBlank(message = "내용은 필수입니다")
    private String content;
    
    // Default constructor for JSON deserialization
    public BoardRequest() {
    }
    
    public BoardRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
