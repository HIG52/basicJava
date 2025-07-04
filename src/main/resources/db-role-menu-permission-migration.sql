-- 역할별 메뉴 권한 테이블 생성
CREATE TABLE role_menu_permissions (
    id NUMBER(19,0) NOT NULL,
    role VARCHAR2(50) NOT NULL,
    menu_id NUMBER(19,0) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR2(100),
    CONSTRAINT pk_role_menu_permissions PRIMARY KEY (id),
    CONSTRAINT fk_role_menu_permissions_menu FOREIGN KEY (menu_id) REFERENCES menus(id),
    CONSTRAINT uk_role_menu_permissions UNIQUE (role, menu_id)
);

-- 시퀀스 생성
CREATE SEQUENCE ROLE_MENU_PERMISSION_SEQ START WITH 1 INCREMENT BY 1;

-- 인덱스 생성
CREATE INDEX idx_role_menu_permissions_role ON role_menu_permissions(role);
CREATE INDEX idx_role_menu_permissions_menu_id ON role_menu_permissions(menu_id);

-- 코멘트 추가
COMMENT ON TABLE role_menu_permissions IS '역할별 메뉴 접근 권한';
COMMENT ON COLUMN role_menu_permissions.id IS '기본키';
COMMENT ON COLUMN role_menu_permissions.role IS '사용자 역할 (ADMIN, USER 등)';
COMMENT ON COLUMN role_menu_permissions.menu_id IS '메뉴 ID';
COMMENT ON COLUMN role_menu_permissions.created_at IS '생성일시';
COMMENT ON COLUMN role_menu_permissions.created_by IS '생성자';
