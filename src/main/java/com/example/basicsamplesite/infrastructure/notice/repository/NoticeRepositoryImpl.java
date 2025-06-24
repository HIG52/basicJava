package com.example.basicsamplesite.infrastructure.notice.repository;

import com.example.basicsamplesite.domain.notice.entity.Notice;
import com.example.basicsamplesite.domain.notice.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Notice Repository 구현체
 */
@Repository
public class NoticeRepositoryImpl implements NoticeRepository {
    
    private final NoticeJpaRepository noticeJpaRepository;
    
    public NoticeRepositoryImpl(NoticeJpaRepository noticeJpaRepository) {
        this.noticeJpaRepository = noticeJpaRepository;
    }
    
    @Override
    public List<Notice> findAll() {
        return noticeJpaRepository.findAll();
    }
    
    @Override
    public List<Notice> findAllWithPaging(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size); // page는 1부터 시작
        Page<Notice> noticePage = noticeJpaRepository.findAllActiveOrderByCreatedAtDesc(pageable, search);
        return noticePage.getContent();
    }
    
    @Override
    public long count() {
        return noticeJpaRepository.countActive();
    }
    
    @Override
    public long countWithSearch(String search) {
        return noticeJpaRepository.countActiveWithSearch(search);
    }
    
    @Override
    public Optional<Notice> findById(Long id) {
        return noticeJpaRepository.findByIdAndNotDeleted(id);
    }
    
    @Override
    public Notice save(Notice notice) {
        return noticeJpaRepository.save(notice);
    }
    
    @Override
    public void deleteById(Long id) {
        noticeJpaRepository.findByIdAndNotDeleted(id)
                .ifPresent(notice -> {
                    notice.delete(); // 소프트 삭제
                    noticeJpaRepository.save(notice);
                });
    }
    
    @Override
    public boolean existsById(Long id) {
        return noticeJpaRepository.findByIdAndNotDeleted(id).isPresent();
    }
}
