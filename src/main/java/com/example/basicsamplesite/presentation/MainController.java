package com.example.basicsamplesite.presentation;

import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import com.example.basicsamplesite.presentation.common.dto.MainResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 메인 페이지 컨트롤러
 */
@RestController
public class MainController {

    /**
     * 메인 메시지 조회
     */
    @GetMapping("/main")
    public ResponseEntity<ApiResponse<MainResponse>> main() {
        MainResponse response = MainResponse.of("환영합니다!");
        return ResponseEntity.ok(ApiResponse.success("메인 메시지 조회 성공", response));
    }
}
