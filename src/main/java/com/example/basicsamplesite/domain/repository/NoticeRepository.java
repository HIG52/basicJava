package com.example.basicsamplesite.domain.repository;

import com.example.basicsamplesite.domain.entity.Notice;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {

    List<Notice> findAll();
    
    List<Notice> findAllByOrderByCreatedAtDesc();
    
    List<Notice> findByCategory(String category);
    
    Optional<Notice> findById(Long id);
    
    Notice save(Notice notice);
    
    long count();
}
