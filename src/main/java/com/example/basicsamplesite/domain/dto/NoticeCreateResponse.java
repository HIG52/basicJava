package com.example.basicsamplesite.domain.dto;

import com.example.basicsamplesite.domain.entity.Notice;

public record NoticeCreateResponse(
        Long id,
        String title,
        String message
) {
    public static NoticeCreateResponse of(Notice notice) {
        return new NoticeCreateResponse(
                notice.getId(),
                notice.getTitle(),
                "공지사항이 성공적으로 생성되었습니다."
        );
    }
    
    public static NoticeCreateResponse fail() {
        return new NoticeCreateResponse(
                null,
                null,
                "공지사항 생성에 실패했습니다."
        );
    }
}
