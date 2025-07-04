package com.example.basicsamplesite.presentation.notice.controller;

import com.example.basicsamplesite.application.notice.command.CreateNoticeCommand;
import com.example.basicsamplesite.application.notice.command.UpdateNoticeCommand;
import com.example.basicsamplesite.application.notice.dto.NoticeApplicationDto;
import com.example.basicsamplesite.application.notice.dto.NoticeListApplicationDto;
import com.example.basicsamplesite.application.notice.query.NoticeListQuery;
import com.example.basicsamplesite.application.notice.service.NoticeService;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import com.example.basicsamplesite.presentation.notice.dto.NoticeListResponse;
import com.example.basicsamplesite.presentation.notice.dto.NoticeRequest;
import com.example.basicsamplesite.presentation.notice.dto.NoticeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 일반 사용자 공지사항 관련 Controller (조회 전용)
 */
@RestController
@RequestMapping("/notices")
public class NoticeController {
    
    private final NoticeService noticeService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<NoticeListResponse>> getNotices(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        try {
            NoticeListQuery query = new NoticeListQuery(page, size, search);

            NoticeListApplicationDto listDto = noticeService.getNoticeList(query);

            List<NoticeResponse> notices = listDto.getNotices().stream()
                    .map(this::convertToNoticeResponse)
                    .collect(Collectors.toList());

            NoticeListResponse response = new NoticeListResponse(
                    notices,
                    listDto.getTotalItems(),
                    listDto.getCurrentPage(),
                    listDto.getItemsPerPage(),
                    listDto.getTotalPages()
            );
            
            return ResponseEntity.ok(ApiResponse.success("공지사항 목록 조회 성공", response));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("공지사항 목록 조회 중 오류가 발생했습니다"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNoticeDetail(@PathVariable Long id) {
        try {
            NoticeApplicationDto noticeDto = noticeService.getNoticeDetail(id);

            NoticeResponse response = convertToNoticeResponse(noticeDto);
            
            return ResponseEntity.ok(ApiResponse.success("공지사항 상세 조회 성공", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("공지사항 상세 조회 중 오류가 발생했습니다"));
        }
    }

    private NoticeResponse convertToNoticeResponse(NoticeApplicationDto dto) {
        return new NoticeResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getAuthor(),
                dto.getCreatedAt().format(dateFormatter),
                dto.getViewCount()
        );
    }
}
