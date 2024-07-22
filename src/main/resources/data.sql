-- 관심 카테고리 셋팅
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('패션',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('라이프스타일',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('뷰티',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('음식/요리',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('예술',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('반려동물',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('여행',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('엔터테인먼트',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('애니메이션',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('키즈',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('스포츠',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO interest (INTEREST_CATEGORY,CREATE_DTM,UPDATE_DTM) VALUES ('게임',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- 탈퇴 설문 항목 셋팅
INSERT INTO user_withdrawl_survey (SURVEY,CREATE_DTM,UPDATE_DTM) VALUES ('원하는 팝업에 대한 정보가 없어요',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO user_withdrawl_survey (SURVEY,CREATE_DTM,UPDATE_DTM) VALUES ('팝업 정보가 적어요',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO user_withdrawl_survey (SURVEY,CREATE_DTM,UPDATE_DTM) VALUES ('이용빈도가 낮아요',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO user_withdrawl_survey (SURVEY,CREATE_DTM,UPDATE_DTM) VALUES ('다시 가입하고 싶어요',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO user_withdrawl_survey (SURVEY,CREATE_DTM,UPDATE_DTM) VALUES ('앱에 오류가 많이 생겨요',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO user_withdrawl_survey (SURVEY,CREATE_DTM,UPDATE_DTM) VALUES ('기타',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- 권한 셋팅
INSERT INTO roles (ROLE,ROLE_DESC,CREATE_DTM,UPDATE_DTM) VALUES ('USER','사용자',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO roles (ROLE,ROLE_DESC,CREATE_DTM,UPDATE_DTM) VALUES ('ADMIN','관리자',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);