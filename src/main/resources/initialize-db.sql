-- 테이블이 이미 존재하는 경우 삭제
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE notices';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

-- 시퀀스가 이미 존재하는 경우 삭제
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE NOTICE_SEQ';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/

-- 시퀀스 생성
CREATE SEQUENCE NOTICE_SEQ
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

-- 테이블 생성
CREATE TABLE notices (
  id NUMBER(19) PRIMARY KEY,
  title VARCHAR2(100) NOT NULL,
  content CLOB NOT NULL,
  category VARCHAR2(50) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  view_count NUMBER(10) DEFAULT 0 NOT NULL
);

-- 샘플 데이터 삽입
INSERT INTO notices (id, title, content, category, created_at, view_count)
VALUES (NOTICE_SEQ.NEXTVAL, '공지사항 1', '공지사항 내용 1입니다.', '일반', SYSTIMESTAMP, 120);

INSERT INTO notices (id, title, content, category, created_at, view_count)
VALUES (NOTICE_SEQ.NEXTVAL, '중요 안내', '중요한 안내사항입니다. 꼭 확인해주세요.', '중요', SYSTIMESTAMP - INTERVAL '3' DAY, 350);

INSERT INTO notices (id, title, content, category, created_at, view_count)
VALUES (NOTICE_SEQ.NEXTVAL, '서비스 점검 안내', '내일 오전 2시부터 4시까지 서비스 점검이 있을 예정입니다.', '점검', SYSTIMESTAMP - INTERVAL '1' DAY, 87);

INSERT INTO notices (id, title, content, category, created_at, view_count)
VALUES (NOTICE_SEQ.NEXTVAL, '신규 기능 안내', '새로운 기능이 추가되었습니다. 많은 이용 부탁드립니다.', '일반', SYSTIMESTAMP, 42);

INSERT INTO notices (id, title, content, category, created_at, view_count)
VALUES (NOTICE_SEQ.NEXTVAL, '이벤트 안내', '신규 이벤트가 시작되었습니다. 많은 참여 부탁드립니다.', '이벤트', SYSTIMESTAMP, 15);

COMMIT;

-- 테이블 내용 확인
SELECT * FROM notices;
