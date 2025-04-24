package com.example.basicsamplesite.domain.service;

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

    public List<Notice> findAllNotices() {
        return noticeRepository.findAllByOrderByCreatedAtDesc();
    }

    public Notice findNoticeById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));
    }

    @Transactional
    public Notice increaseViewCount(Long id) {
        Notice notice = findNoticeById(id);
        notice.increaseViewCount();
        return notice;
    }

    @Transactional
    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }
}
