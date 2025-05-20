package com.example.basicsamplesite.domain.service;

import com.example.basicsamplesite.domain.dto.NoticeCreateRequest;
import com.example.basicsamplesite.domain.dto.NoticeCreateResponse;
import com.example.basicsamplesite.domain.dto.NoticeDetailResponse;
import com.example.basicsamplesite.domain.dto.NoticeSummaryResponse;
import com.example.basicsamplesite.domain.entity.Notice;
import com.example.basicsamplesite.domain.repository.NoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeService noticeService;

    private Notice testNotice1;
    private Notice testNotice2;

    @BeforeEach
    void setUp() {
        // 테스트용 Notice 객체 생성을 위한 리플렉션 사용
        testNotice1 = Notice.createNotice("테스트 공지사항 1", "테스트 내용입니다 1");
        setPrivateField(testNotice1, "id", 1L);
        setPrivateField(testNotice1, "createdAt", LocalDateTime.now().minusDays(1));
        setPrivateField(testNotice1, "viewCount", 5);

        testNotice2 = Notice.createNotice("테스트 공지사항 2", "테스트 내용입니다 2");
        setPrivateField(testNotice2, "id", 2L);
        setPrivateField(testNotice2, "createdAt", LocalDateTime.now().minusDays(2));
        setPrivateField(testNotice2, "viewCount", 10);
    }

    // 리플렉션을 사용하여 private 필드 값 설정
    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set private field: " + fieldName, e);
        }
    }

    @Test
    @DisplayName("모든 공지사항 목록 조회 - 성공")
    void findAllNotices_ShouldReturnAllNotices() {
        // given
        List<Notice> noticeList = Arrays.asList(testNotice1, testNotice2);
        given(noticeRepository.findAllByOrderByCreatedAtDesc()).willReturn(noticeList);

        // when
        List<NoticeSummaryResponse> responses = noticeService.findAllNotices();

        // then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).id()).isEqualTo(1L);
        assertThat(responses.get(0).title()).isEqualTo("테스트 공지사항 1");
        assertThat(responses.get(1).id()).isEqualTo(2L);
        assertThat(responses.get(1).title()).isEqualTo("테스트 공지사항 2");
        
        verify(noticeRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("ID로 공지사항 조회 - 성공")
    void findNoticeById_WhenExists_ShouldReturnNotice() {
        // given
        Long noticeId = 1L;
        given(noticeRepository.findById(noticeId)).willReturn(Optional.of(testNotice1));

        // when
        NoticeDetailResponse response = noticeService.findNoticeById(noticeId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(noticeId);
        assertThat(response.title()).isEqualTo("테스트 공지사항 1");
        assertThat(response.content()).isEqualTo("테스트 내용입니다 1");
        assertThat(response.viewCount()).isEqualTo(5);
        
        verify(noticeRepository, times(1)).findById(noticeId);
    }

    @Test
    @DisplayName("ID로 공지사항 조회 - 실패(존재하지 않는 ID)")
    void findNoticeById_WhenNotExists_ShouldThrowException() {
        // given
        Long noticeId = 999L;
        given(noticeRepository.findById(noticeId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> noticeService.findNoticeById(noticeId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Notice not found with id: " + noticeId);
        
        verify(noticeRepository, times(1)).findById(noticeId);
    }

    @Test
    @DisplayName("조회수 증가 - 성공")
    void increaseViewCount_WhenExists_ShouldIncreaseAndReturnNotice() {
        // given
        Long noticeId = 1L;
        int initialViewCount = testNotice1.getViewCount();
        given(noticeRepository.findById(noticeId)).willReturn(Optional.of(testNotice1));

        // when
        NoticeDetailResponse response = noticeService.increaseViewCount(noticeId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(noticeId);
        assertThat(response.viewCount()).isEqualTo(initialViewCount + 1);
        
        verify(noticeRepository, times(1)).findById(noticeId);
    }

    @Test
    @DisplayName("공지사항 생성 - 성공")
    void createNotice_ShouldCreateAndReturnNotice() {
        // given
        NoticeCreateRequest request = new NoticeCreateRequest("새 공지사항", "새 공지사항 내용입니다");
        Notice newNotice = Notice.createNotice(request.title(), request.content());
        setPrivateField(newNotice, "id", 3L);
        
        given(noticeRepository.save(any(Notice.class))).willReturn(newNotice);

        // when
        NoticeCreateResponse response = noticeService.createNotice(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(3L);
        assertThat(response.title()).isEqualTo("새 공지사항");
        assertThat(response.message()).contains("성공적으로 생성");
        
        verify(noticeRepository, times(1)).save(any(Notice.class));
    }

    @Test
    @DisplayName("공지사항 생성 - 실패")
    void createNotice_WhenFails_ShouldReturnFailResponse() {
        // given
        NoticeCreateRequest request = new NoticeCreateRequest("새 공지사항", "새 공지사항 내용입니다");
        given(noticeRepository.save(any(Notice.class))).willThrow(new RuntimeException("데이터베이스 오류"));

        // when
        NoticeCreateResponse response = noticeService.createNotice(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isNull();
        assertThat(response.title()).isNull();
        assertThat(response.message()).contains("실패");
        
        verify(noticeRepository, times(1)).save(any(Notice.class));
    }
}
