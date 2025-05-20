package com.example.basicsamplesite.domain.service;

import com.example.basicsamplesite.domain.dto.NoticeCreateRequest;
import com.example.basicsamplesite.domain.dto.NoticeCreateResponse;
import com.example.basicsamplesite.domain.dto.NoticeDetailResponse;
import com.example.basicsamplesite.domain.dto.NoticeSummaryResponse;
import com.example.basicsamplesite.domain.entity.Notice;
import com.example.basicsamplesite.domain.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<NoticeSummaryResponse> findAllNotices() {
        List<Notice> notices = noticeRepository.findAllByOrderByCreatedAtDesc();
        return NoticeSummaryResponse.listFrom(notices);
    }

    public NoticeDetailResponse findNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));
        return NoticeDetailResponse.from(notice);
    }

    @Transactional
    public NoticeDetailResponse increaseViewCount(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));
        notice.increaseViewCount();
        return NoticeDetailResponse.from(notice);
    }

    @Transactional
    public NoticeCreateResponse createNotice(NoticeCreateRequest request) {
        try {
            Notice notice = Notice.createNotice(request.title(), request.content());
            Notice savedNotice = noticeRepository.save(notice);
            
            return NoticeCreateResponse.of(savedNotice);
        } catch (Exception e) {
            return NoticeCreateResponse.fail();
        }
    }
}
