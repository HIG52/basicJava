package com.example.basicsamplesite.presentation;

import com.example.basicsamplesite.domain.dto.NoticeCreateRequest;
import com.example.basicsamplesite.domain.dto.NoticeCreateResponse;
import com.example.basicsamplesite.domain.dto.NoticeDetailResponse;
import com.example.basicsamplesite.domain.dto.NoticeSummaryResponse;
import com.example.basicsamplesite.domain.service.NoticeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoticeController.class)
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoticeService noticeService;

    @Test
    @DisplayName("모든 공지사항 목록 조회 - 성공")
    void getAllNotices_ShouldReturnAllNotices() throws Exception {
        // given
        NoticeSummaryResponse notice1 = new NoticeSummaryResponse(1L, "공지사항 1", LocalDateTime.now(), "2023-01-01 12:00:00", 5);
        NoticeSummaryResponse notice2 = new NoticeSummaryResponse(2L, "공지사항 2", LocalDateTime.now(), "2023-01-02 12:00:00", 10);
        List<NoticeSummaryResponse> notices = Arrays.asList(notice1, notice2);
        
        given(noticeService.findAllNotices()).willReturn(notices);

        // when & then
        mockMvc.perform(get("/api/notices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("공지사항 1"))
                .andExpect(jsonPath("$[0].viewCount").value(5))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("공지사항 2"))
                .andExpect(jsonPath("$[1].viewCount").value(10));
        
        verify(noticeService, times(1)).findAllNotices();
    }

    @Test
    @DisplayName("ID로 공지사항 조회 - 성공")
    void getNoticeById_ShouldReturnNotice() throws Exception {
        // given
        Long noticeId = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        NoticeDetailResponse notice = new NoticeDetailResponse(
                noticeId, "공지사항 제목", "공지사항 내용", createdAt, "2023-01-01 12:00:00", 5);
        
        given(noticeService.increaseViewCount(noticeId)).willReturn(notice);

        // when & then
        mockMvc.perform(get("/api/notices/{id}", noticeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(noticeId))
                .andExpect(jsonPath("$.title").value("공지사항 제목"))
                .andExpect(jsonPath("$.content").value("공지사항 내용"))
                .andExpect(jsonPath("$.viewCount").value(5));
        
        verify(noticeService, times(1)).increaseViewCount(noticeId);
    }

    @Test
    @DisplayName("공지사항 생성 - 성공")
    void createNotice_ShouldCreateAndReturnCreated() throws Exception {
        // given
        NoticeCreateRequest request = new NoticeCreateRequest("새 공지사항", "새 공지사항 내용");
        NoticeCreateResponse response = new NoticeCreateResponse(1L, "새 공지사항", "공지사항이 성공적으로 생성되었습니다.");
        
        given(noticeService.createNotice(any(NoticeCreateRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/notices")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("새 공지사항"))
                .andExpect(jsonPath("$.message").value("공지사항이 성공적으로 생성되었습니다."));
        
        verify(noticeService, times(1)).createNotice(any(NoticeCreateRequest.class));
    }

    @Test
    @DisplayName("공지사항 생성 - 유효성 검사 실패")
    void createNotice_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        // given
        NoticeCreateRequest request = new NoticeCreateRequest("", ""); // 빈 제목과 내용

        // when & then
        mockMvc.perform(post("/api/notices")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("공지사항 생성 - 서버 오류")
    void createNotice_WhenServerError_ShouldReturnBadRequest() throws Exception {
        // given
        NoticeCreateRequest request = new NoticeCreateRequest("새 공지사항", "새 공지사항 내용");
        NoticeCreateResponse response = new NoticeCreateResponse(null, null, "공지사항 생성에 실패했습니다.");
        
        given(noticeService.createNotice(any(NoticeCreateRequest.class))).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/notices")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.title").doesNotExist())
                .andExpect(jsonPath("$.message").value("공지사항 생성에 실패했습니다."));
        
        verify(noticeService, times(1)).createNotice(any(NoticeCreateRequest.class));
    }
}
