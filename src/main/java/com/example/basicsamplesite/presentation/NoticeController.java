package com.example.basicsamplesite.presentation;

import com.example.basicsamplesite.domain.dto.NoticeCreateRequest;
import com.example.basicsamplesite.domain.dto.NoticeCreateResponse;
import com.example.basicsamplesite.domain.dto.NoticeDetailResponse;
import com.example.basicsamplesite.domain.dto.NoticeSummaryResponse;
import com.example.basicsamplesite.domain.service.NoticeService;
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
    public ResponseEntity<List<NoticeSummaryResponse>> getAllNotices() {
        List<NoticeSummaryResponse> responses = noticeService.findAllNotices();
        return ResponseEntity.ok(responses);
    }

    // ID로 특정 공지사항 조회
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDetailResponse> getNoticeById(@PathVariable Long id) {
        NoticeDetailResponse response = noticeService.increaseViewCount(id); // 조회수 증가
        return ResponseEntity.ok(response);
    }
    
    // 공지사항 생성
    @PostMapping
    public ResponseEntity<?> createNotice(@Valid @RequestBody NoticeCreateRequest request) {
        NoticeCreateResponse response = noticeService.createNotice(request);
        
        if (response.id() != null) {
            return ResponseEntity.created(URI.create("/api/notices/" + response.id()))
                    .body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
