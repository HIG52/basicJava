package com.example.basicsamplesite.infrastructure.config;

import com.example.basicsamplesite.application.menu.service.MenuService;
import com.example.basicsamplesite.application.menu.service.RoleMenuPermissionService;
import com.example.basicsamplesite.domain.menu.entity.Menu;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
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
    private final MenuService menuService;
    private final RoleMenuPermissionService roleMenuPermissionService;
    private final PasswordEncoder passwordEncoder;
    
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            log.info("=== 초기 데이터 생성 시작 ===");
            
            try {
                // 관리자 계정이 없으면 생성
                Optional<User> existingAdmin = userRepository.findByUserId("test123");
                if (!existingAdmin.isPresent()) {
                    User admin = User.builder()
                            .userId("test123")
                            .username("TestAdmin")
                            .email("testadmin@test.com")
                            .password(passwordEncoder.encode("test123123"))
                            .role(User.UserRole.ADMIN)
                            .phone("010-0000-0000")
                            .address("테스트시 테스트구 테스트로 123")
                            .zipcode("12345")
                            .addressDetail("테스트빌딩 1층")
                            .birthDate(LocalDate.of(1990, 1, 1))
                            .isActive(true)
                            .build();
                    userRepository.save(admin);
                    log.info("관리자 계정 생성 완료: test123");
                } else {
                    log.info("관리자 계정이 이미 존재합니다");
                }
                
                // 일반 사용자 계정들 생성
                Optional<User> existingUser1 = userRepository.findByUsername("hong123");
                if (!existingUser1.isPresent()) {
                    User user1 = User.builder()
                            .userId("hong123")
                            .username("hong123")
                            .email("hong@example.com")
                            .password(passwordEncoder.encode("password123!"))
                            .role(User.UserRole.USER)
                            .phone("010-1111-2222")
                            .address("부산광역시 해운대구 해운대해변로 264")
                            .zipcode("48094")
                            .addressDetail("해운대타워 5층")
                            .birthDate(LocalDate.of(1990, 5, 15))
                            .isActive(true)
                            .build();
                    userRepository.save(user1);
                    log.info("사용자 계정 (hong123) 생성 완료");
                }
                
                Optional<User> existingUser2 = userRepository.findByUsername("kim456");
                if (!existingUser2.isPresent()) {
                    User user2 = User.builder()
                            .userId("kim456")
                            .username("kim456")
                            .email("kim@example.com")
                            .password(passwordEncoder.encode("password123!"))
                            .role(User.UserRole.USER)
                            .phone("010-3333-4444")
                            .address("대구광역시 중구 동성로 123")
                            .zipcode("41911")
                            .addressDetail("대구백화점 근처")
                            .birthDate(LocalDate.of(1985, 8, 20))
                            .isActive(true)
                            .build();
                    userRepository.save(user2);
                    log.info("사용자 계정 (kim456) 생성 완료");
                }
                
                // 추가 테스트 사용자들
                Optional<User> existingUser3 = userRepository.findByUsername("lee789");
                if (!existingUser3.isPresent()) {
                    User user3 = User.builder()
                            .userId("lee789")
                            .username("lee789")
                            .email("lee@example.com")
                            .password(passwordEncoder.encode("password123!"))
                            .role(User.UserRole.USER)
                            .phone("010-5555-6666")
                            .address("인천광역시 연수구 송도동 123")
                            .zipcode("21984")
                            .addressDetail("송도센트럴파크 아파트 101동 501호")
                            .birthDate(LocalDate.of(1992, 12, 10))
                            .isActive(true)
                            .build();
                    userRepository.save(user3);
                    log.info("사용자 계정 (lee789) 생성 완료");
                }
                
                Optional<User> existingUser4 = userRepository.findByUsername("park000");
                if (!existingUser4.isPresent()) {
                    User user4 = User.builder()
                            .userId("park000")
                            .username("park000")
                            .email("park@example.com")
                            .password(passwordEncoder.encode("password123!"))
                            .role(User.UserRole.USER)
                            .phone("010-7777-8888")
                            .address("광주광역시 서구 상무대로 123")
                            .zipcode("61949")
                            .addressDetail("상무센터시티 B동 1502호")
                            .birthDate(LocalDate.of(1988, 3, 25))
                            .isActive(true)
                            .build();
                    userRepository.save(user4);
                    log.info("사용자 계정 (park000) 생성 완료");
                }
                
                // 비활성 사용자 계정 (테스트용)
                Optional<User> existingInactiveUser = userRepository.findByUsername("inactive");
                if (!existingInactiveUser.isPresent()) {
                    User inactiveUser = User.builder()
                            .userId("inactive")
                            .username("inactive")
                            .email("inactive@example.com")
                            .password(passwordEncoder.encode("password123!"))
                            .role(User.UserRole.USER)
                            .phone("010-9999-0000")
                            .address("대전광역시 유성구 대학로 123")
                            .zipcode("34141")
                            .addressDetail("유성구청 근처")
                            .birthDate(LocalDate.of(1995, 7, 8))
                            .isActive(false) // 비활성 계정
                            .build();
                    userRepository.save(inactiveUser);
                    log.info("비활성 사용자 계정 (inactive) 생성 완료");
                }
                
                log.info("=== 초기 데이터 생성 완료 ===");
                
                // kim456 계정으로 게시판 더미 데이터 생성
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

            Optional<User> kimUser = userRepository.findByUsername("kim456");
            if (!kimUser.isPresent()) {
                log.warn("kim456 계정을 찾을 수 없습니다. 자유게시판 더미 데이터 생성을 건너뜁니다.");
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
                    author.getUsername(),
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

            Optional<User> adminUser = userRepository.findByUserId("test123");
            if (!adminUser.isPresent()) {
                log.warn("test123 계정을 찾을 수 없습니다. 공지사항 더미 데이터 생성을 건너뜁니다.");
                return;
            }

            User author = adminUser.get();

            for (int i = 1; i <= 20; i++) {
                Notice notice = Notice.createNotice(
                    "공지사항 제목 " + i,
                    "공지사항 내용입니다. " + i + "번째 공지사항의 상세한 내용을 담고 있습니다.\n\n" +
                    "이 공지사항은 테스트용 더미 데이터로 생성되었습니다.\n" +
                    "중요한 사이트 운영 정보와 업데이트 사항을 알려드립니다.\n\n" +
                    "공지사항 번호: " + i + "\n" +
                    "반드시 확인해 주시기 바랍니다.",
                    author.getUsername(),
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

            Optional<User> hongUser = userRepository.findByUsername("hong123");
            Optional<User> leeUser = userRepository.findByUsername("lee789");
            
            if (!hongUser.isPresent() || !leeUser.isPresent()) {
                log.warn("필요한 사용자 계정을 찾을 수 없습니다. QnA 더미 데이터 생성을 건너뜁니다.");
                return;
            }

            User author1 = hongUser.get();
            User author2 = leeUser.get();

            for (int i = 1; i <= 20; i++) {
                // 번갈아가며 다른 사용자로 생성
                User currentAuthor = (i % 2 == 1) ? author1 : author2;
                
                Qna qna = Qna.createQna(
                    "QnA 질문 제목 " + i,
                    "QnA 질문 내용입니다. " + i + "번째 질문의 상세한 내용을 담고 있습니다.\n\n" +
                    "이 질문은 테스트용 더미 데이터로 생성되었습니다.\n" +
                    "사이트 이용과 관련된 궁금한 점을 문의드립니다.\n\n" +
                    "질문 번호: " + i + "\n" +
                    "빠른 답변 부탁드립니다.",
                    currentAuthor.getUsername(),
                    currentAuthor.getId()
                );
                qnaRepository.save(qna);
            }

            log.info("QnA 더미 데이터 20개 생성 완료");
            log.info("=== 모든 게시판 더미 데이터 생성 완료 ===");

        } catch (Exception e) {
            log.error("QnA 더미 데이터 생성 중 오류 발생: {}", e.getMessage(), e);
        }
    }
}
