package com.example.basicsamplesite.domain.qna.entity;

import com.example.basicsamplesite.domain.common.entity.BaseEntity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Q&A 답변 엔티티
 */
@Entity
@Table(name = "qna_answer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaAnswer extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QNA_ANSWER_SEQ_GENERATOR")
    @SequenceGenerator(name = "QNA_ANSWER_SEQ_GENERATOR", sequenceName = "QNA_ANSWER_SEQ", allocationSize = 1)
    private Long id;
    
    @Lob
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false, length = 50)
    private String author;
    
    @Column(nullable = false, name = "author_id")
    private Long authorId;
    
    @Column(nullable = false, columnDefinition = "NUMBER(1,0) DEFAULT 0", name = "is_admin_answer")
    @Builder.Default
    private Boolean isAdminAnswer = false;
    
    @Column(nullable = false, name = "qna_id")
    private Long qnaId;

    public void updateContent(String content) {
        this.content = content;
    }
}
