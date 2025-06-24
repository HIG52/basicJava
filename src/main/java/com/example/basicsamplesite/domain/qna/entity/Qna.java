package com.example.basicsamplesite.domain.qna.entity;

import com.example.basicsamplesite.domain.common.entity.BaseEntity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Q&A 엔티티
 */
@Entity
@Table(name = "qna")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Qna extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QNA_SEQ_GENERATOR")
    @SequenceGenerator(name = "QNA_SEQ_GENERATOR", sequenceName = "QNA_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Lob
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false, length = 50)
    private String author;
    
    @Column(nullable = false, name = "author_id")
    private Long authorId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    @Builder.Default
    private QnaStatus status = QnaStatus.WAITING;
    
    @Column(nullable = false, name = "is_answered", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    @Builder.Default
    private Boolean isAnswered = false;
    
    @Column(nullable = false, name = "answer_count")
    @Builder.Default
    private Long answerCount = 0L;

    public enum QnaStatus {
        WAITING, ANSWERED, CLOSED
    }

    public void updateQna(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addAnswer() {
        this.answerCount++;
        this.isAnswered = true;
        this.status = QnaStatus.ANSWERED;
    }

    public void updateStatus(QnaStatus status) {
        this.status = status;
    }

    public static Qna createQna(String title, String content, String author, Long authorId) {
        return Qna.builder()
                .title(title)
                .content(content)
                .author(author != null ? author : "익명")
                .authorId(authorId)
                .build();
    }
}
