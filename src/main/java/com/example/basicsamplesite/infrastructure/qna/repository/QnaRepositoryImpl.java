package com.example.basicsamplesite.infrastructure.qna.repository;

import com.example.basicsamplesite.domain.qna.entity.Qna;
import com.example.basicsamplesite.domain.qna.repository.QnaRepository;
import com.example.basicsamplesite.infrastructure.qna.repository.jpa.QnaJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * QnA Repository 구현체
 */
@Repository
public class QnaRepositoryImpl implements QnaRepository {
    
    private final QnaJpaRepository qnaJpaRepository;
    
    public QnaRepositoryImpl(QnaJpaRepository qnaJpaRepository) {
        this.qnaJpaRepository = qnaJpaRepository;
    }
    
    @Override
    public Qna save(Qna qna) {
        return qnaJpaRepository.save(qna);
    }
    
    @Override
    public List<Qna> findAllWithPaging(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page - 1, size); // page는 1부터 시작
        
        Page<Qna> qnaPage;
        if (search != null && !search.trim().isEmpty()) {
            qnaPage = qnaJpaRepository.findAllWithSearch(search.trim(), pageable);
        } else {
            qnaPage = qnaJpaRepository.findAllOrdered(pageable);
        }
        
        return qnaPage.getContent();
    }
    
    @Override
    public long count(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return qnaJpaRepository.countWithSearch(search.trim());
        } else {
            return qnaJpaRepository.countAll();
        }
    }
    
    @Override
    public Optional<Qna> findById(Long id) {
        return qnaJpaRepository.findById(id);
    }
    
    @Override
    public void deleteById(Long id) {
        qnaJpaRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return qnaJpaRepository.existsById(id);
    }
}
