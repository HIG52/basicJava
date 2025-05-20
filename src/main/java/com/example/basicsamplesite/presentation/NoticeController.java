package com.example.basicsamplesite.presentation;

import com.example.basicsamplesite.domain.dto.NoticeCreateResponse;
import com.example.basicsamplesite.domain.dto.NoticeDetailResponse;
import com.example.basicsamplesite.domain.dto.NoticeSummaryResponse;
import com.example.basicsamplesite.domain.service.NoticeService;
import com.example.basicsamplesite.presentation.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 모든 공지사항 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<NoticeSummaryDto>>> getAllNotices() {
        List<NoticeSummaryResponse> responses = noticeService.findAllNotices();
        List<NoticeSummaryDto> dtos = NoticeSummaryDto.listFrom(responses);
        return ResponseEntity.ok(ApiResponse.success(dtos, "공지사항 목록 조회 성공"));
    }

    // ID로 특정 공지사항 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoticeDetailDto>> getNoticeById(@PathVariable Long id) {
        NoticeDetailResponse response = noticeService.increaseViewCount(id); // 조회수 증가
        NoticeDetailDto dto = NoticeDetailDto.from(response);
        return ResponseEntity.ok(ApiResponse.success(dto, "공지사항 상세 조회 성공"));
    }
    
    // 공지사항 생성
    @PostMapping
    public ResponseEntity<ApiResponse<NoticeCreateResponseDto>> createNotice(@Valid @RequestBody NoticeCreateDto request) {
        NoticeCreateResponse response = noticeService.createNotice(request.toDomain());
        NoticeCreateResponseDto dto = NoticeCreateResponseDto.from(response);
        
        if (dto.success()) {
            return ResponseEntity.created(URI.create("/api/notices/" + dto.id()))
                    .body(ApiResponse.success(dto, "공지사항 생성 성공"));
        } else {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("공지사항 생성 실패: " + dto.message()));
        }
    }
}
