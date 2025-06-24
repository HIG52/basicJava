-- 테이블들이 이미 존재하는 경우 삭제 (역순으로 삭제)
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE qna_answer';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE qna';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE board';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE users';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE notices';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

-- 시퀀스들이 이미 존재하는 경우 삭제
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE NOTICE_SEQ';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE BOARD_SEQ';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE QNA_SEQ';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE QNA_ANSWER_SEQ';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE USER_SEQ';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/

-- 시퀀스들 생성
CREATE SEQUENCE NOTICE_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE BOARD_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE QNA_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE QNA_ANSWER_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE USER_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- 1. 회원 테이블 생성
CREATE TABLE users (
    id NUMBER(19) PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL,
    role VARCHAR2(20) DEFAULT 'USER' NOT NULL,
    is_active NUMBER(1) DEFAULT 1 NOT NULL,
    last_login_at TIMESTAMP,
    phone VARCHAR2(20),
    address VARCHAR2(200),
    zipcode VARCHAR2(10),
    address_detail VARCHAR2(200),
    birth_date DATE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- 2. 공지사항 테이블 생성
CREATE TABLE notices (
    id NUMBER(19) PRIMARY KEY,
    title VARCHAR2(200) NOT NULL,
    content CLOB NOT NULL,
    author VARCHAR2(50) DEFAULT '관리자' NOT NULL,
    author_id NUMBER(19) NOT NULL,
    views NUMBER(19) DEFAULT 0 NOT NULL,
    is_important NUMBER(1) DEFAULT 0 NOT NULL,
    is_deleted NUMBER(1) DEFAULT 0 NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- 3. 게시판 테이블 생성
CREATE TABLE board (
    id NUMBER(19) PRIMARY KEY,
    title VARCHAR2(200) NOT NULL,
    content CLOB NOT NULL,
    author VARCHAR2(50) NOT NULL,
    author_id NUMBER(19) NOT NULL,
    views NUMBER(19) DEFAULT 0 NOT NULL,
    is_deleted NUMBER(1) DEFAULT 0 NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- 4. Q&A 테이블 생성
CREATE TABLE qna (
    id NUMBER(19) PRIMARY KEY,
    title VARCHAR2(200) NOT NULL,
    content CLOB NOT NULL,
    author VARCHAR2(50) NOT NULL,
    author_id NUMBER(19) NOT NULL,
    views NUMBER(19) DEFAULT 0 NOT NULL,
    status VARCHAR2(20) DEFAULT 'WAITING' NOT NULL,
    is_answered NUMBER(1) DEFAULT 0 NOT NULL,
    answer_count NUMBER(19) DEFAULT 0 NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- 5. Q&A 답변 테이블 생성
CREATE TABLE qna_answer (
    id NUMBER(19) PRIMARY KEY,
    content CLOB NOT NULL,
    author VARCHAR2(50) NOT NULL,
    author_id NUMBER(19) NOT NULL,
    is_admin_answer NUMBER(1) DEFAULT 0 NOT NULL,
    qna_id NUMBER(19) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_qna_answer_qna FOREIGN KEY (qna_id) REFERENCES qna(id)
);

-- 외래키 제약 조건 추가
ALTER TABLE notices ADD CONSTRAINT fk_notices_author FOREIGN KEY (author_id) REFERENCES users(id);
ALTER TABLE board ADD CONSTRAINT fk_board_author FOREIGN KEY (author_id) REFERENCES users(id);
ALTER TABLE qna ADD CONSTRAINT fk_qna_author FOREIGN KEY (author_id) REFERENCES users(id);
ALTER TABLE qna_answer ADD CONSTRAINT fk_qna_answer_author FOREIGN KEY (author_id) REFERENCES users(id);

-- 샘플 데이터 삽입

-- 1. 회원 데이터 삽입
INSERT INTO users (id, name, email, password, role, is_active, phone, created_at, updated_at)
VALUES (USER_SEQ.NEXTVAL, '관리자', 'admin@example.com', 'password123', 'ADMIN', 1, '010-1234-5678', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO users (id, name, email, password, role, is_active, phone, created_at, updated_at)
VALUES (USER_SEQ.NEXTVAL, '홍길동', 'hong@example.com', 'password123', 'USER', 1, '010-1111-2222', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO users (id, name, email, password, role, is_active, phone, created_at, updated_at)
VALUES (USER_SEQ.NEXTVAL, '김철수', 'kim@example.com', 'password123', 'USER', 1, '010-3333-4444', SYSTIMESTAMP, SYSTIMESTAMP);

-- 2. 공지사항 데이터 삽입
INSERT INTO notices (id, title, content, author, author_id, views, is_important, created_at, updated_at)
VALUES (NOTICE_SEQ.NEXTVAL, '사이트 이용안내', '사이트 이용에 관한 공지사항입니다', '관리자', 1, 150, 1, SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO notices (id, title, content, author, author_id, views, is_important, created_at, updated_at)
VALUES (NOTICE_SEQ.NEXTVAL, '시스템 점검 안내', '시스템 점검이 예정되어 있습니다.', '관리자', 1, 42, 0, SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO notices (id, title, content, author, author_id, views, is_important, created_at, updated_at)
VALUES (NOTICE_SEQ.NEXTVAL, '새로운 기능 업데이트', '새로운 기능이 추가되었습니다.', '관리자', 1, 128, 0, SYSTIMESTAMP, SYSTIMESTAMP);

-- 3. 게시판 데이터 삽입
INSERT INTO board (id, title, content, author, author_id, views, created_at, updated_at)
VALUES (BOARD_SEQ.NEXTVAL, '첫 번째 게시글입니다', '게시글 내용입니다', '홍길동', 2, 42, SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO board (id, title, content, author, author_id, views, created_at, updated_at)
VALUES (BOARD_SEQ.NEXTVAL, '안녕하세요', '반갑습니다. 첫 게시글을 올립니다.', '김철수', 3, 25, SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO board (id, title, content, author, author_id, views, created_at, updated_at)
VALUES (BOARD_SEQ.NEXTVAL, '질문이 있습니다', '이 사이트는 어떻게 사용하나요?', '홍길동', 2, 18, SYSTIMESTAMP, SYSTIMESTAMP);

-- 4. Q&A 데이터 삽입
INSERT INTO qna (id, title, content, author, author_id, views, status, created_at, updated_at)
VALUES (QNA_SEQ.NEXTVAL, 'React 관련 질문입니다', 'React Hook 사용법에 대해 질문드립니다', '김철수', 3, 25, 'WAITING', SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO qna (id, title, content, author, author_id, views, status, is_answered, answer_count, created_at, updated_at)
VALUES (QNA_SEQ.NEXTVAL, 'Spring Boot 관련 문의', 'Spring Boot 설정 방법을 알고 싶습니다', '홍길동', 2, 35, 'ANSWERED', 1, 1, SYSTIMESTAMP, SYSTIMESTAMP);

-- 5. Q&A 답변 데이터 삽입
INSERT INTO qna_answer (id, content, author, author_id, is_admin_answer, qna_id, created_at, updated_at)
VALUES (QNA_ANSWER_SEQ.NEXTVAL, 'Spring Boot는 다음과 같이 설정할 수 있습니다...', '관리자', 1, 1, 2, SYSTIMESTAMP, SYSTIMESTAMP);

COMMIT;

-- 테이블 내용 확인
SELECT 'USERS' as table_name, COUNT(*) as count FROM users
UNION ALL
SELECT 'NOTICES' as table_name, COUNT(*) as count FROM notices
UNION ALL
SELECT 'BOARD' as table_name, COUNT(*) as count FROM board
UNION ALL
SELECT 'QNA' as table_name, COUNT(*) as count FROM qna
UNION ALL
SELECT 'QNA_ANSWER' as table_name, COUNT(*) as count FROM qna_answer;
