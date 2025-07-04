-- User 테이블에 userId 컬럼 추가
-- 기존 username 컬럼은 실제 사용자 이름으로 사용
-- userId 컬럼은 로그인 시 사용하는 고유 아이디로 사용

-- 1. userId 컬럼 추가 (임시로 nullable)
ALTER TABLE users ADD COLUMN user_id VARCHAR(20);

-- 2. 기존 데이터가 있다면 username 값을 userId로 복사
UPDATE users SET user_id = username WHERE user_id IS NULL;

-- 3. userId 컬럼을 NOT NULL과 UNIQUE로 변경
ALTER TABLE users ALTER COLUMN user_id SET NOT NULL;
ALTER TABLE users ADD CONSTRAINT uk_users_user_id UNIQUE (user_id);

-- 4. username 컬럼의 길이를 50자로 변경 (실제 이름 저장용)
ALTER TABLE users ALTER COLUMN username TYPE VARCHAR(50);

-- 참고: 기존 데이터가 있는 경우
-- - username에 있던 로그인 아이디는 user_id로 이동
-- - username은 실제 사용자 이름으로 수동 업데이트 필요
-- 예: UPDATE users SET username = '홍길동' WHERE user_id = 'hong123';
