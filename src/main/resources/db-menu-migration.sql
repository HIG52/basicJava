-- Menu 관련 테이블 생성 스크립트

-- 시퀀스 생성 (이미 존재하는 경우 삭제 후 재생성)
BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE MENU_SEQ';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE MENU_PERMISSION_SEQ';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -2289 THEN
         RAISE;
      END IF;
END;
/

-- 테이블 삭제 (이미 존재하는 경우)
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE menu_permissions';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE menus';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

-- 시퀀스 생성
CREATE SEQUENCE MENU_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE MENU_PERMISSION_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- 1. 메뉴 테이블 생성
CREATE TABLE menus (
    id NUMBER(19) PRIMARY KEY,
    menu_name VARCHAR2(100) NOT NULL,
    menu_path VARCHAR2(200) NOT NULL,
    description VARCHAR2(500),
    is_active NUMBER(1) DEFAULT 1 NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

-- 2. 메뉴 권한 테이블 생성
CREATE TABLE menu_permissions (
    id NUMBER(19) PRIMARY KEY,
    user_id NUMBER(19) NOT NULL,
    menu_id NUMBER(19) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_menu_permissions_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_menu_permissions_menu FOREIGN KEY (menu_id) REFERENCES menus(id),
    CONSTRAINT uk_menu_permissions_user_menu UNIQUE (user_id, menu_id)
);

-- 기본 메뉴 데이터 삽입
INSERT INTO menus (id, menu_name, menu_path, description, is_active, created_at, updated_at) 
VALUES (MENU_SEQ.NEXTVAL, '홈', '/', '메인 홈페이지', 1, SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO menus (id, menu_name, menu_path, description, is_active, created_at, updated_at) 
VALUES (MENU_SEQ.NEXTVAL, '게시판', '/boards', '자유게시판', 1, SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO menus (id, menu_name, menu_path, description, is_active, created_at, updated_at) 
VALUES (MENU_SEQ.NEXTVAL, '공지사항', '/notices', '공지사항 게시판', 1, SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO menus (id, menu_name, menu_path, description, is_active, created_at, updated_at) 
VALUES (MENU_SEQ.NEXTVAL, 'Q&A', '/qnas', '질문과 답변 게시판', 1, SYSTIMESTAMP, SYSTIMESTAMP);

INSERT INTO menus (id, menu_name, menu_path, description, is_active, created_at, updated_at) 
VALUES (MENU_SEQ.NEXTVAL, '관리자', '/admin', '관리자 메뉴', 1, SYSTIMESTAMP, SYSTIMESTAMP);

COMMIT;

-- 테이블 생성 확인
SELECT 'MENUS' as table_name, COUNT(*) as count FROM menus
UNION ALL
SELECT 'MENU_PERMISSIONS' as table_name, COUNT(*) as count FROM menu_permissions;
