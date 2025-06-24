package com.example.basicsamplesite.application.notice.service;

import com.example.basicsamplesite.application.notice.command.CreateNoticeCommand;
import com.example.basicsamplesite.application.notice.command.UpdateNoticeCommand;
import com.example.basicsamplesite.application.notice.dto.NoticeApplicationDto;
import com.example.basicsamplesite.application.notice.dto.NoticeListApplicationDto;
import com.example.basicsamplesite.application.notice.query.NoticeListQuery;
import com.example.basicsamplesite.domain.notice.entity.Notice;
import com.example.basicsamplesite.domain.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 공지사항 관련 Application Service
 */
@Service
@Transactional
public class NoticeService {
    
    private final NoticeRepository noticeRepository;
    
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Transactional(readOnly = true)
    public NoticeListApplicationDto getNoticeList(NoticeListQuery query) {
        List<Notice> notices = noticeRepository.findAllWithPaging(query.getPage(), query.getSize(), query.getSearch());
        long totalItems = noticeRepository.countWithSearch(query.getSearch());
        
        List<NoticeApplicationDto> noticeList = notices.stream()
                .map(this::convertToNoticeApplicationDto)
                .collect(Collectors.toList());
        
        int totalPages = (int) Math.ceil((double) totalItems / query.getSize());
        
        return new NoticeListApplicationDto(
                noticeList,
                totalItems,
                query.getPage(),
                query.getSize(),
                totalPages
        );
    }

    @Transactional(readOnly = true)
    public NoticeApplicationDto getNoticeDetail(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지사항입니다"));
        
        // 조회수 증가
        notice.increaseViewCount();
        noticeRepository.save(notice);
        
        return convertToNoticeApplicationDto(notice);
    }

    public NoticeApplicationDto createNotice(CreateNoticeCommand command) {
        Notice notice = Notice.createNotice(
                command.getTitle(),
                command.getContent(),
                "관리자", // 실제로는 현재 로그인 사용자
                1L // 실제로는 현재 로그인 사용자 ID
        );
        
        Notice savedNotice = noticeRepository.save(notice);
        return convertToNoticeApplicationDto(savedNotice);
    }

    public NoticeApplicationDto updateNotice(UpdateNoticeCommand command) {
        Notice notice = noticeRepository.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지사항입니다"));
        
        notice.updateContent(command.getTitle(), command.getContent());
        Notice savedNotice = noticeRepository.save(notice);
        
        return convertToNoticeApplicationDto(savedNotice);
    }

    public void deleteNotice(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 공지사항입니다");
        }
        
        noticeRepository.deleteById(id);
    }

    private NoticeApplicationDto convertToNoticeApplicationDto(Notice notice) {
        return new NoticeApplicationDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getAuthor(),
                notice.getCreatedAt(),
                notice.getViewCount()
        );
    }
}
