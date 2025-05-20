package com.example.basicsamplesite.infrastructure.repositoryImpl;

import com.example.basicsamplesite.domain.entity.Notice;
import com.example.basicsamplesite.infrastructure.repository.NoticeJpaRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoticeRepositoryImplTest {

    @Mock
    private NoticeJpaRepository noticeJpaRepository;

    @InjectMocks
    private NoticeRepositoryImpl noticeRepository;

    @Test
    @DisplayName("모든 공지사항 조회")
    void findAll_ShouldReturnAllNotices() {
        // given
        Notice notice1 = createNoticeWithId(1L, "제목1", "내용1");
        Notice notice2 = createNoticeWithId(2L, "제목2", "내용2");
        List<Notice> expectedNotices = Arrays.asList(notice1, notice2);
        
        given(noticeJpaRepository.findAll()).willReturn(expectedNotices);

        // when
        List<Notice> actualNotices = noticeRepository.findAll();

        // then
        assertThat(actualNotices).hasSize(2);
        assertThat(actualNotices).isEqualTo(expectedNotices);
        verify(noticeJpaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("공지사항 생성 날짜 역순 조회")
    void findAllByOrderByCreatedAtDesc_ShouldReturnNoticesInDescOrder() {
        // given
        Notice notice1 = createNoticeWithId(1L, "제목1", "내용1");
        Notice notice2 = createNoticeWithId(2L, "제목2", "내용2");
        List<Notice> expectedNotices = Arrays.asList(notice1, notice2);
        
        given(noticeJpaRepository.findAllByOrderByCreatedAtDesc()).willReturn(expectedNotices);

        // when
        List<Notice> actualNotices = noticeRepository.findAllByOrderByCreatedAtDesc();

        // then
        assertThat(actualNotices).hasSize(2);
        assertThat(actualNotices).isEqualTo(expectedNotices);
        verify(noticeJpaRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("ID로 공지사항 조회")
    void findById_ShouldReturnNotice() {
        // given
        Long noticeId = 1L;
        Notice expectedNotice = createNoticeWithId(noticeId, "제목1", "내용1");
        
        given(noticeJpaRepository.findById(noticeId)).willReturn(Optional.of(expectedNotice));

        // when
        Optional<Notice> actualNotice = noticeRepository.findById(noticeId);

        // then
        assertThat(actualNotice).isPresent();
        assertThat(actualNotice.get()).isEqualTo(expectedNotice);
        verify(noticeJpaRepository, times(1)).findById(noticeId);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 공지사항 조회")
    void findById_WhenNotExists_ShouldReturnEmpty() {
        // given
        Long noticeId = 999L;
        
        given(noticeJpaRepository.findById(noticeId)).willReturn(Optional.empty());

        // when
        Optional<Notice> actualNotice = noticeRepository.findById(noticeId);

        // then
        assertThat(actualNotice).isEmpty();
        verify(noticeJpaRepository, times(1)).findById(noticeId);
    }

    @Test
    @DisplayName("공지사항 저장")
    void save_ShouldSaveAndReturnNotice() {
        // given
        Notice noticeToSave = Notice.createNotice("새 공지사항", "새 내용");
        Notice savedNotice = createNoticeWithId(3L, "새 공지사항", "새 내용");
        
        given(noticeJpaRepository.save(noticeToSave)).willReturn(savedNotice);

        // when
        Notice actualSavedNotice = noticeRepository.save(noticeToSave);

        // then
        assertThat(actualSavedNotice).isEqualTo(savedNotice);
        verify(noticeJpaRepository, times(1)).save(noticeToSave);
    }

    @Test
    @DisplayName("공지사항 개수 조회")
    void count_ShouldReturnNumberOfNotices() {
        // given
        long expectedCount = 5L;
        
        given(noticeJpaRepository.count()).willReturn(expectedCount);

        // when
        long actualCount = noticeRepository.count();

        // then
        assertThat(actualCount).isEqualTo(expectedCount);
        verify(noticeJpaRepository, times(1)).count();
    }

    // Helper method to create Notice objects for testing
    private Notice createNoticeWithId(Long id, String title, String content) {
        Notice notice = Notice.createNotice(title, content);
        
        // Use reflection to set the id
        try {
            var field = Notice.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(notice, id);
            
            field = Notice.class.getDeclaredField("createdAt");
            field.setAccessible(true);
            field.set(notice, LocalDateTime.now());
            
            field = Notice.class.getDeclaredField("viewCount");
            field.setAccessible(true);
            field.set(notice, 0);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to set private field", e);
        }
        
        return notice;
    }
}
