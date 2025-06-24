package com.example.basicsamplesite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 통합 테스트 클래스
 */
@SpringBootTest
class BasicSampleSiteApplicationTests {

    @Test
    void contextLoads() {
        // 스프링 컨텍스트가 정상적으로 로드되는지 확인
    }

    // Notice 관련 테스트는 현재 Notice 기능이 구현되지 않아 주석 처리
    /*
    @Autowired
    private NoticeService noticeService;

    @Test
    @DisplayName("공지사항 CRUD 통합 테스트")
    @Transactional
    @Rollback
    void testNoticeIntegration() {
        // 1. 공지사항 생성 테스트
        NoticeCreateRequest createRequest = new NoticeCreateRequest("통합 테스트 공지사항", "통합 테스트 내용입니다.");
        NoticeCreateResponse createResponse = noticeService.createNotice(createRequest);
        
        assertThat(createResponse).isNotNull();
        assertThat(createResponse.success()).isTrue();
        assertThat(createResponse.message()).isEqualTo("공지사항이 성공적으로 생성되었습니다.");
        
        Long noticeId = createResponse.id();
        
        // 2. 공지사항 상세 조회 테스트
        NoticeDetailResponse detailResponse = noticeService.findNoticeById(noticeId);
        
        assertThat(detailResponse).isNotNull();
        assertThat(detailResponse.title()).isEqualTo("통합 테스트 공지사항");
        assertThat(detailResponse.content()).isEqualTo("통합 테스트 내용입니다.");
        assertThat(detailResponse.viewCount()).isEqualTo(0);
        
        // 3. 조회수 증가 테스트
        NoticeDetailResponse increasedViewResponse = noticeService.increaseViewCount(noticeId);
        
        assertThat(increasedViewResponse).isNotNull();
        assertThat(increasedViewResponse.viewCount()).isEqualTo(1);
        
        // 4. 공지사항 목록 조회 테스트
        List<NoticeSummaryResponse> allNotices = noticeService.findAllNotices();
        
        assertThat(allNotices).isNotEmpty();
        assertThat(allNotices).anyMatch(notice -> notice.id().equals(noticeId));
        
        // 5. 공지사항 삭제는 실제로 수행하지 않음 (다른 테스트에 영향)
    }
    */
}
