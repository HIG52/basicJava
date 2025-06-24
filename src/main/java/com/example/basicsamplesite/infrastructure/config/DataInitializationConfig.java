package com.example.basicsamplesite.infrastructure.config;

import com.example.basicsamplesite.domain.board.entity.Board;
import com.example.basicsamplesite.domain.board.repository.BoardRepository;
import com.example.basicsamplesite.domain.notice.entity.Notice;
import com.example.basicsamplesite.domain.notice.repository.NoticeRepository;
import com.example.basicsamplesite.domain.qna.entity.Qna;
import com.example.basicsamplesite.domain.qna.repository.QnaRepository;
import com.example.basicsamplesite.domain.user.entity.User;
import com.example.basicsamplesite.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

/**
 * 개발 환경에서 초기 데이터를 생성하는 설정
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
@Profile("dev") // 개발 환경에서만 실행
public class DataInitializationConfig {
    
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final NoticeRepository noticeRepository;
    private final QnaRepository qnaRepository;
    
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            log.info("=== 초기 데이터 생성 시작 ===");
            
            try {
                // 관리자 계정이 없으면 생성
                Optional<User> existingAdmin = userRepository.findByEmail("admin@example.com");
                if (!existingAdmin.isPresent()) {
                    User admin = User.builder()
                            .name("관리자")
                            .email("admin@example.com")
                            .password("password123")
                            .role(User.UserRole.ADMIN)
                            .phone("010-1234-5678")
                            .build();
                    userRepository.save(admin);
                    log.info("관리자 계정 생성 완료");
                } else {
                    log.info("관리자 계정이 이미 존재합니다");
                }
                
                // 일반 사용자 계정들 생성
                Optional<User> existingUser1 = userRepository.findByEmail("hong@example.com");
                if (!existingUser1.isPresent()) {
                    User user1 = User.builder()
                            .name("홍길동")
                            .email("hong@example.com")
                            .password("password123")
                            .role(User.UserRole.USER)
                            .phone("010-1111-2222")
                            .build();
                    userRepository.save(user1);
                    log.info("사용자 계정 (홍길동) 생성 완료");
                }
                
                Optional<User> existingUser2 = userRepository.findByEmail("kim@example.com");
                if (!existingUser2.isPresent()) {
                    User user2 = User.builder()
                            .name("김철수")
                            .email("kim@example.com")
                            .password("password123")
                            .role(User.UserRole.USER)
                            .phone("010-3333-4444")
                            .build();
                    userRepository.save(user2);
                    log.info("사용자 계정 (김철수) 생성 완료");
                }
                
                log.info("=== 초기 데이터 생성 완료 ===");
                
                // 김철수 계정으로 게시판 더미 데이터 생성
                createBoardDummyData();
                createNoticeDummyData();
                createQnaDummyData();

            } catch (Exception e) {
                log.error("초기 데이터 생성 중 오류 발생: {}", e.getMessage(), e);
            }
        };
    }

    private void createBoardDummyData() {
        try {
            log.info("=== 자유게시판 더미 데이터 생성 시작 ===");

            Optional<User> kimUser = userRepository.findByEmail("kim@example.com");
            if (!kimUser.isPresent()) {
                log.warn("김철수 계정을 찾을 수 없습니다. 자유게시판 더미 데이터 생성을 건너뜁니다.");
                return;
            }

            User author = kimUser.get();

            for (int i = 1; i <= 20; i++) {
                Board board = Board.createBoard(
                    "자유게시판 제목 " + i,
                    "자유게시판 내용입니다. " + i + "번째 게시글의 상세한 내용을 담고 있습니다.\n\n" +
                    "이 게시글은 테스트용 더미 데이터로 생성되었습니다.\n" +
                    "다양한 주제에 대해 자유롭게 이야기할 수 있는 공간입니다.\n\n" +
                    "게시글 번호: " + i,
                    author.getName(),
                    author.getId()
                );
                boardRepository.save(board);
            }

            log.info("자유게시판 더미 데이터 20개 생성 완료");

        } catch (Exception e) {
            log.error("자유게시판 더미 데이터 생성 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    private void createNoticeDummyData() {
        try {
            log.info("=== 공지사항 더미 데이터 생성 시작 ===");

            Optional<User> kimUser = userRepository.findByEmail("kim@example.com");
            if (!kimUser.isPresent()) {
                log.warn("김철수 계정을 찾을 수 없습니다. 공지사항 더미 데이터 생성을 건너뜁니다.");
                return;
            }

            User author = kimUser.get();

            for (int i = 1; i <= 20; i++) {
                Notice notice = Notice.createNotice(
                    "공지사항 제목 " + i,
                    "공지사항 내용입니다. " + i + "번째 공지사항의 상세한 내용을 담고 있습니다.\n\n" +
                    "이 공지사항은 테스트용 더미 데이터로 생성되었습니다.\n" +
                    "중요한 사이트 운영 정보와 업데이트 사항을 알려드립니다.\n\n" +
                    "공지사항 번호: " + i + "\n" +
                    "반드시 확인해 주시기 바랍니다.",
                    author.getName(),
                    author.getId()
                );
                noticeRepository.save(notice);
            }

            log.info("공지사항 더미 데이터 20개 생성 완료");

        } catch (Exception e) {
            log.error("공지사항 더미 데이터 생성 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    private void createQnaDummyData() {
        try {
            log.info("=== QnA 더미 데이터 생성 시작 ===");

            Optional<User> kimUser = userRepository.findByEmail("kim@example.com");
            if (!kimUser.isPresent()) {
                log.warn("김철수 계정을 찾을 수 없습니다. QnA 더미 데이터 생성을 건너뜁니다.");
                return;
            }

            User author = kimUser.get();

            for (int i = 1; i <= 20; i++) {
                Qna qna = Qna.createQna(
                    "QnA 질문 제목 " + i,
                    "QnA 질문 내용입니다. " + i + "번째 질문의 상세한 내용을 담고 있습니다.\n\n" +
                    "이 질문은 테스트용 더미 데이터로 생성되었습니다.\n" +
                    "사이트 이용과 관련된 궁금한 점을 문의드립니다.\n\n" +
                    "질문 번호: " + i + "\n" +
                    "빠른 답변 부탁드립니다.",
                    author.getName(),
                    author.getId()
                );
                qnaRepository.save(qna);
            }

            log.info("QnA 더미 데이터 20개 생성 완료");
            log.info("=== 모든 게시판 더미 데이터 생성 완료 ===");

        } catch (Exception e) {
            log.error("QnA 더미 데이터 생성 중 오류 발생: {}", e.getMessage(), e);
        }
    };
}

