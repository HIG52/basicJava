package com.example.basicsamplesite.presentation;

import com.example.basicsamplesite.domain.dto.NoticeCreateRequest;
import com.example.basicsamplesite.domain.entity.Notice;
import com.example.basicsamplesite.domain.service.NoticeService;
import com.example.basicsamplesite.presentation.dto.NoticeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 모든 공지사항 목록 조회
    @GetMapping
    public ResponseEntity<List<NoticeResponse>> getAllNotices() {
        List<Notice> notices = noticeService.findAllNotices();
        List<NoticeResponse> responses = notices.stream()
                .map(NoticeResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // ID로 특정 공지사항 조회
    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getNoticeById(@PathVariable Long id) {
        Notice notice = noticeService.increaseViewCount(id); // 조회수 증가
        return ResponseEntity.ok(NoticeResponse.from(notice));
    }
    
    // 공지사항 생성
    @PostMapping
    public ResponseEntity<NoticeResponse> createNotice(@Valid @RequestBody NoticeCreateRequest request) {
        Notice newNotice = noticeService.createNotice(request.toEntity());
        return ResponseEntity
                .created(URI.create("/api/notices/" + newNotice.getId()))
                .body(NoticeResponse.from(newNotice));
    }
}
