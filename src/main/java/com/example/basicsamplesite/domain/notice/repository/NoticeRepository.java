package com.example.basicsamplesite.domain.notice.repository;

import com.example.basicsamplesite.domain.notice.entity.Notice;

import java.util.List;
import java.util.Optional;

/**
 * Notice Repository 인터페이스
 */
public interface NoticeRepository {

    List<Notice> findAll();

    List<Notice> findAllWithPaging(int page, int size, String search);

    long count();

    long countWithSearch(String search);

    Optional<Notice> findById(Long id);

    Notice save(Notice notice);

    void deleteById(Long id);

    boolean existsById(Long id);
}
