package com.example.basicsamplesite.domain.notice.entity;

import com.example.basicsamplesite.domain.common.entity.BaseEntity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공지사항 엔티티
 */
@Entity
@Table(name = "notices")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICE_SEQ_GENERATOR")
    @SequenceGenerator(name = "NOTICE_SEQ_GENERATOR", sequenceName = "NOTICE_SEQ", allocationSize = 1)
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

    @Column(nullable = false, name = "views")
    @Builder.Default
    private Long views = 0L;
    
    @Column(nullable = false, name = "is_important", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    @Builder.Default
    private Boolean isImportant = false;
    
    @Column(nullable = false, name = "is_deleted", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    @Builder.Default
    private Boolean isDeleted = false;

    public static Notice createNotice(String title, String content, String author, Long authorId) {
        return Notice.builder()
                .title(title)
                .content(content)
                .author(author != null ? author : "관리자")
                .authorId(authorId)
                .build();
    }

    public void increaseViewCount() {
        this.views++;
    }

    public void updateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void setImportant(Boolean isImportant) {
        this.isImportant = isImportant;
    }

    public int getViewCount() {
        return this.views != null ? this.views.intValue() : 0;
    }
}
