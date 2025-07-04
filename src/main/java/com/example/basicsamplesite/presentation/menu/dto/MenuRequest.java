package com.example.basicsamplesite.presentation.menu.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 메뉴 작성/수정 요청 DTO - Presentation Layer
 */
@Getter
public class MenuRequest {
    
    @NotBlank(message = "메뉴명은 필수입니다.")
    @Size(max = 100, message = "메뉴명은 100자 이하여야 합니다.")
    private String menuName;
    
    @NotBlank(message = "메뉴 경로는 필수입니다.")
    @Size(max = 200, message = "메뉴 경로는 200자 이하여야 합니다.")
    private String menuPath;
    
    @Size(max = 500, message = "설명은 500자 이하여야 합니다.")
    private String description;
    
    // Default constructor for JSON deserialization
    public MenuRequest() {}
    
    public MenuRequest(String menuName, String menuPath, String description) {
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.description = description;
    }
}
