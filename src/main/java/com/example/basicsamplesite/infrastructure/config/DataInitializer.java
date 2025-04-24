package com.example.basicsamplesite.infrastructure.config;

import com.example.basicsamplesite.domain.entity.Notice;
import com.example.basicsamplesite.domain.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final NoticeRepository noticeRepository;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // 초기 데이터 확인
            long count = noticeRepository.count();
            logger.info("현재 DB에 {} 개의 공지사항이 있습니다.", count);
        };
    }
}
