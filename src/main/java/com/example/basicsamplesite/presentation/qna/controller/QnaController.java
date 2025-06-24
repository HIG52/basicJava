package com.example.basicsamplesite.presentation.qna.controller;

import com.example.basicsamplesite.application.qna.command.CreateQnaCommand;
import com.example.basicsamplesite.application.qna.command.UpdateQnaCommand;
import com.example.basicsamplesite.application.qna.dto.QnaApplicationDto;
import com.example.basicsamplesite.application.qna.dto.QnaListApplicationDto;
import com.example.basicsamplesite.application.qna.query.QnaListQuery;
import com.example.basicsamplesite.application.qna.service.QnaService;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import com.example.basicsamplesite.presentation.qna.dto.QnaListResponse;
import com.example.basicsamplesite.presentation.qna.dto.QnaRequest;
import com.example.basicsamplesite.presentation.qna.dto.QnaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qnas")
public class QnaController {
    
    private final QnaService qnaService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<QnaListResponse>> getQnas(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        try {
            QnaListQuery query = new QnaListQuery(page, size, search);

            QnaListApplicationDto listDto = qnaService.getQnaList(query);

            List<QnaResponse> qnas = listDto.getList().stream()
                    .map(this::convertToQnaResponse)
                    .collect(Collectors.toList());
            
            QnaListResponse response = new QnaListResponse(
                    qnas,
                    listDto.getTotalItems(),
                    listDto.getCurrentPage(),
                    listDto.getItemsPerPage(),
                    listDto.getTotalPages()
            );
            
            return ResponseEntity.ok(ApiResponse.success("Q&A 목록 조회 성공", response));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Q&A 목록 조회 중 오류가 발생했습니다"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QnaResponse>> getQnaDetail(@PathVariable Long id) {
        try {
            QnaApplicationDto qnaDto = qnaService.getQnaDetail(id);

            QnaResponse response = convertToQnaResponse(qnaDto);
            
            return ResponseEntity.ok(ApiResponse.success("Q&A 상세 조회 성공", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Q&A 상세 조회 중 오류가 발생했습니다"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QnaResponse>> createQna(@Valid @RequestBody QnaRequest request) {
        try {
            CreateQnaCommand command = new CreateQnaCommand(request.getTitle(), request.getContent());

            QnaApplicationDto qnaDto = qnaService.createQna(command);

            QnaResponse response = convertToQnaResponse(qnaDto);
            
            return ResponseEntity.ok(ApiResponse.success("Q&A 작성 성공", response));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Q&A 작성 중 오류가 발생했습니다"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QnaResponse>> updateQna(
            @PathVariable Long id, 
            @Valid @RequestBody QnaRequest request) {
        try {
            UpdateQnaCommand command = new UpdateQnaCommand(id, request.getTitle(), request.getContent());

            QnaApplicationDto qnaDto = qnaService.updateQna(command);

            QnaResponse response = convertToQnaResponse(qnaDto);
            
            return ResponseEntity.ok(ApiResponse.success("Q&A 수정 성공", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Q&A 수정 중 오류가 발생했습니다"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteQna(@PathVariable Long id) {
        try {
            qnaService.deleteQna(id);
            
            return ResponseEntity.ok(ApiResponse.success("Q&A 삭제 성공"));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Q&A 삭제 중 오류가 발생했습니다"));
        }
    }

    private QnaResponse convertToQnaResponse(QnaApplicationDto dto) {
        return new QnaResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getAuthor(),
                dto.getCreatedAt().format(dateFormatter)
        );
    }
}
