package com.example.basicsamplesite.presentation.board.controller;

import com.example.basicsamplesite.application.board.command.CreateBoardCommand;
import com.example.basicsamplesite.application.board.command.UpdateBoardCommand;
import com.example.basicsamplesite.application.board.dto.BoardApplicationDto;
import com.example.basicsamplesite.application.board.dto.BoardListApplicationDto;
import com.example.basicsamplesite.application.board.query.BoardListQuery;
import com.example.basicsamplesite.application.board.service.BoardService;
import com.example.basicsamplesite.presentation.board.dto.BoardListResponse;
import com.example.basicsamplesite.presentation.board.dto.BoardRequest;
import com.example.basicsamplesite.presentation.board.dto.BoardResponse;
import com.example.basicsamplesite.presentation.common.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시판 관련 Controller
 */
@RestController
@RequestMapping("/boards")
public class BoardController {
    
    private final BoardService boardService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<BoardListResponse>> getBoards(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        try {
            BoardListQuery query = new BoardListQuery(page, size, search);

            BoardListApplicationDto listDto = boardService.getBoardList(query);

            List<BoardResponse> boards = listDto.getList().stream()
                    .map(this::convertToBoardResponse)
                    .collect(Collectors.toList());
            
            BoardListResponse response = new BoardListResponse(
                    boards,
                    listDto.getTotalItems(),
                    listDto.getCurrentPage(),
                    listDto.getItemsPerPage(),
                    listDto.getTotalPages()
            );
            
            return ResponseEntity.ok(ApiResponse.success("게시판 목록 조회 성공", response));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("게시판 목록 조회 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 게시글 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponse>> getBoardDetail(@PathVariable Long id) {
        try {
            // Application Service 호출
            BoardApplicationDto boardDto = boardService.getBoardDetail(id);
            
            // Application DTO → Presentation Response 변환
            BoardResponse response = convertToBoardResponse(boardDto);
            
            return ResponseEntity.ok(ApiResponse.success("게시글 상세 조회 성공", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("게시글 상세 조회 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 게시글 작성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponse>> createBoard(@Valid @RequestBody BoardRequest request) {
        try {
            // Presentation DTO → Application Command 변환
            CreateBoardCommand command = new CreateBoardCommand(request.getTitle(), request.getContent());
            
            // Application Service 호출
            BoardApplicationDto boardDto = boardService.createBoard(command);
            
            // Application DTO → Presentation Response 변환
            BoardResponse response = convertToBoardResponse(boardDto);
            
            return ResponseEntity.ok(ApiResponse.success("게시글 작성 성공", response));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("게시글 작성 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 게시글 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponse>> updateBoard(
            @PathVariable Long id, 
            @Valid @RequestBody BoardRequest request) {
        try {
            // Presentation DTO → Application Command 변환
            UpdateBoardCommand command = new UpdateBoardCommand(id, request.getTitle(), request.getContent());
            
            // Application Service 호출
            BoardApplicationDto boardDto = boardService.updateBoard(command);
            
            // Application DTO → Presentation Response 변환
            BoardResponse response = convertToBoardResponse(boardDto);
            
            return ResponseEntity.ok(ApiResponse.success("게시글 수정 성공", response));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("게시글 수정 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBoard(@PathVariable Long id) {
        try {
            // Application Service 호출
            boardService.deleteBoard(id);
            
            return ResponseEntity.ok(ApiResponse.success("게시글 삭제 성공"));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("게시글 삭제 중 오류가 발생했습니다"));
        }
    }
    
    /**
     * BoardApplicationDto를 BoardResponse로 변환
     */
    private BoardResponse convertToBoardResponse(BoardApplicationDto dto) {
        return new BoardResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getAuthor(),
                dto.getCreatedAt().format(dateFormatter),
                dto.getViews()
        );
    }
}
