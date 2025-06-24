package com.example.basicsamplesite.application.qna.service;

import com.example.basicsamplesite.application.qna.command.CreateQnaCommand;
import com.example.basicsamplesite.application.qna.command.UpdateQnaCommand;
import com.example.basicsamplesite.application.qna.dto.QnaApplicationDto;
import com.example.basicsamplesite.application.qna.dto.QnaListApplicationDto;
import com.example.basicsamplesite.application.qna.query.QnaListQuery;
import com.example.basicsamplesite.domain.qna.entity.Qna;
import com.example.basicsamplesite.domain.qna.repository.QnaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * QnA 관련 Application Service
 */
@Service
@Transactional
public class QnaService {
    
    private final QnaRepository qnaRepository;
    
    public QnaService(QnaRepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    @Transactional(readOnly = true)
    public QnaListApplicationDto getQnaList(QnaListQuery query) {
        List<Qna> qnas = qnaRepository.findAllWithPaging(
                query.getPage(), 
                query.getSize(), 
                query.getSearch()
        );
        long totalItems = qnaRepository.count(query.getSearch());
        
        List<QnaApplicationDto> qnaList = qnas.stream()
                .map(this::convertToQnaApplicationDto)
                .collect(Collectors.toList());
        
        int totalPages = (int) Math.ceil((double) totalItems / query.getSize());
        
        return new QnaListApplicationDto(
                qnaList,
                totalItems,
                query.getPage(),
                query.getSize(),
                totalPages
        );
    }

    @Transactional(readOnly = true)
    public QnaApplicationDto getQnaDetail(Long id) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Q&A입니다"));
        
        return convertToQnaApplicationDto(qna);
    }

    public QnaApplicationDto createQna(CreateQnaCommand command) {
        Qna qna = Qna.createQna(
                command.getTitle(),
                command.getContent(),
                "홍길동", // 실제로는 현재 로그인 사용자
                1L // 실제로는 현재 로그인 사용자 ID
        );
        
        Qna savedQna = qnaRepository.save(qna);
        return convertToQnaApplicationDto(savedQna);
    }

    public QnaApplicationDto updateQna(UpdateQnaCommand command) {
        Qna qna = qnaRepository.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Q&A입니다"));
        
        qna.updateQna(command.getTitle(), command.getContent());
        Qna savedQna = qnaRepository.save(qna);
        
        return convertToQnaApplicationDto(savedQna);
    }

    public void deleteQna(Long id) {
        if (!qnaRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 Q&A입니다");
        }
        
        qnaRepository.deleteById(id);
    }

    private QnaApplicationDto convertToQnaApplicationDto(Qna qna) {
        return new QnaApplicationDto(
                qna.getId(),
                qna.getTitle(),
                qna.getContent(),
                qna.getAuthor(),
                qna.getCreatedAt()
        );
    }
}
