package com.example.basicsamplesite.infrastructure.repositoryImpl;

import com.example.basicsamplesite.domain.entity.Notice;
import com.example.basicsamplesite.domain.repository.NoticeRepository;
import com.example.basicsamplesite.infrastructure.repository.NoticeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeJpaRepository noticeJpaRepository;

    @Override
    public List<Notice> findAll() {
        return noticeJpaRepository.findAll();
    }

    @Override
    public List<Notice> findAllByOrderByCreatedAtDesc() {
        return noticeJpaRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Notice> findByCategory(String category) {
        return noticeJpaRepository.findByCategory(category);
    }

    @Override
    public Optional<Notice> findById(Long id) {
        return noticeJpaRepository.findById(id);
    }

    @Override
    public Notice save(Notice notice) {
        return noticeJpaRepository.save(notice);
    }

    @Override
    public long count() {
        return noticeJpaRepository.count();
    }
}
