package com.example.basicsamplesite.domain.service;

/**
 * NoticeServiceTest - Notice 기능이 구현되지 않아 주석 처리
 */
public class NoticeServiceTest {
    // Notice 관련 기능이 현재 구조에 없어서 테스트를 주석 처리합니다.
    // 추후 Notice 기능 구현 시 테스트를 활성화할 수 있습니다.
}

/* 원본 테스트 코드 (주석 처리됨)
import com.example.basicsamplesite.domain.dto.NoticeCreateRequest;
import com.example.basicsamplesite.domain.dto.NoticeCreateResponse;
import com.example.basicsamplesite.domain.dto.NoticeDetailResponse;
import com.example.basicsamplesite.domain.dto.NoticeSummaryResponse;
import com.example.basicsamplesite.domain.entity.Notice;
import com.example.basicsamplesite.domain.repository.NoticeRepository;
import com.example.basicsamplesite.domain.service.NoticeService;
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

import static org.assertj.core.api.Assertions.*;
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
        // 테스트용 공지사항 엔티티 생성
        testNotice1 = Notice.createNotice("테스트 공지사항 1", "테스트 내용입니다 1");
        testNotice1.setId(1L);
        testNotice1.setCreatedAt(LocalDateTime.now().minusDays(1));
        testNotice1.setUpdatedAt(LocalDateTime.now().minusDays(1));

        testNotice2 = Notice.createNotice("테스트 공지사항 2", "테스트 내용입니다 2");
        testNotice2.setId(2L);
        testNotice2.setCreatedAt(LocalDateTime.now());
        testNotice2.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("공지사항 목록 조회 - 성공")
    void findAllNotices_Success() {
        // given
        List<Notice> noticeList = Arrays.asList(testNotice1, testNotice2);
        given(noticeRepository.findAllByOrderByCreatedAtDesc()).willReturn(noticeList);

        // when
        List<NoticeSummaryResponse> responses = noticeService.findAllNotices();

        // then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).title()).isEqualTo("테스트 공지사항 2"); // 최신순으로 정렬되어 첫 번째가 2번
        assertThat(responses.get(1).title()).isEqualTo("테스트 공지사항 1");

        verify(noticeRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("공지사항 상세 조회 - 성공")
    void findNoticeById_Success() {
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
        assertThat(response.viewCount()).isEqualTo(0);

        verify(noticeRepository, times(1)).findById(noticeId);
    }

    // ... 더 많은 테스트 메서드들
}
*/
