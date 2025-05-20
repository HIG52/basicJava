package com.example.basicsamplesite;

import com.example.basicsamplesite.domain.dto.NoticeCreateRequest;
import com.example.basicsamplesite.domain.dto.NoticeCreateResponse;
import com.example.basicsamplesite.domain.dto.NoticeDetailResponse;
import com.example.basicsamplesite.domain.dto.NoticeSummaryResponse;
import com.example.basicsamplesite.domain.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BasicSampleSiteApplicationTests {

    @Autowired
    private NoticeService noticeService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("통합 테스트 - 공지사항 생성 및 조회")
    @Transactional // 테스트 후 롤백
    void createAndFindNotice_IntegrationTest() {
        // 1. 공지사항 생성
        NoticeCreateRequest createRequest = new NoticeCreateRequest("통합 테스트 공지사항", "통합 테스트 내용입니다.");
        NoticeCreateResponse createResponse = noticeService.createNotice(createRequest);
        
        assertThat(createResponse).isNotNull();
        assertThat(createResponse.id()).isNotNull();
        assertThat(createResponse.title()).isEqualTo("통합 테스트 공지사항");
        assertThat(createResponse.message()).contains("성공적으로 생성");

        Long noticeId = createResponse.id();

        // 2. ID로 공지사항 조회
        NoticeDetailResponse detailResponse = noticeService.findNoticeById(noticeId);
        
        assertThat(detailResponse).isNotNull();
        assertThat(detailResponse.id()).isEqualTo(noticeId);
        assertThat(detailResponse.title()).isEqualTo("통합 테스트 공지사항");
        assertThat(detailResponse.content()).isEqualTo("통합 테스트 내용입니다.");
        assertThat(detailResponse.viewCount()).isEqualTo(0);

        // 3. 조회수 증가 확인
        NoticeDetailResponse increasedViewResponse = noticeService.increaseViewCount(noticeId);
        
        assertThat(increasedViewResponse).isNotNull();
        assertThat(increasedViewResponse.viewCount()).isEqualTo(1);

        // 4. 모든 공지사항 목록 조회
        List<NoticeSummaryResponse> allNotices = noticeService.findAllNotices();
        
        assertThat(allNotices).isNotEmpty();
        assertThat(allNotices.stream().anyMatch(notice -> notice.id().equals(noticeId))).isTrue();
    }
}
