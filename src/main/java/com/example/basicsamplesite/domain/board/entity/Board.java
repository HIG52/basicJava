package com.example.basicsamplesite.domain.board.entity;

import com.example.basicsamplesite.domain.common.entity.BaseEntity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시판 엔티티
 */
@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    @SequenceGenerator(name = "BOARD_SEQ_GENERATOR", sequenceName = "BOARD_SEQ", allocationSize = 1)
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
    
    @Column(nullable = false)
    @Builder.Default
    private Long views = 0L;
    
    @Column(nullable = false, name = "is_deleted", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    @Builder.Default
    private Boolean isDeleted = false;

    public void incrementViews() {
        this.views++;
    }

    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public static Board createBoard(String title, String content, String author, Long authorId) {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author != null ? author : "익명")
                .authorId(authorId)
                .build();
    }

    public int getViewCount() {
        return this.views != null ? this.views.intValue() : 0;
    }
}
