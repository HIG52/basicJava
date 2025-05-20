package com.example.basicsamplesite.presentation;

import com.example.basicsamplesite.domain.dto.MainResponse;
import com.example.basicsamplesite.presentation.dto.ApiResponse;
import com.example.basicsamplesite.presentation.dto.MainResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/main")
    public ResponseEntity<ApiResponse<MainResponseDto>> home() {
        MainResponse domainResponse = MainResponse.of("Hello World");
        MainResponseDto dto = MainResponseDto.from(domainResponse);
        return ResponseEntity.ok(ApiResponse.success(dto, "메인 페이지 조회 성공"));
    }
}
