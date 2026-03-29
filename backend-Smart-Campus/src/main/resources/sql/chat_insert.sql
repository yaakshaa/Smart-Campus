-- 질문 (사용자가 챗봇에게 한 질문)
INSERT INTO chat_Question(question_content, question_created_at) VALUES ('메뉴', SYSDATE);
INSERT INTO chat_Question(question_content, question_created_at) VALUES ('도서관', SYSDATE);
INSERT INTO chat_Question(question_content, question_created_at) VALUES ('책', SYSDATE);
INSERT INTO chat_Question(question_content, question_created_at) VALUES ('위치', SYSDATE);
INSERT INTO chat_Question(question_content, question_created_at) VALUES ('수강', SYSDATE);
-- 챗봇 선택지 (distractor)
INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(1, '오늘 메뉴 보러가기', '/restaurants', 1);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(2, '학생식당 보기', '/menu/2?date=2025-07-22', 1);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(1, '도서관 책 찾아보기', '/library/books/bookLists', 2);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(2, '도서관 자리 ', '/library/reserve', 2);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(3, '도서관 마이페이지 ', '/library/mypage', 2);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(1, '도서관 책 찾아보기', '/library/books/bookLists', 3);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(2, '도서관 자리 ', '/library/reserve', 3);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(3, '도서관 마이페이지 ', '/library/mypage', 3);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(1, '지도 ', '/map', 4);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(1, '수강신청 ', '/lectures', 5);

INSERT INTO chat_Distractor(distractor_display_order, distractor_content, distractor_link, question_id)
VALUES 
(2, '내수강신청 목록 ', '/my-enrollments', 5);


-- 사용자 문의
INSERT INTO chat_Inquiry(inquiry_email,inquiry_title, inquiry_message, inquiry_created_at,inquiry_answered,
admin_id, inquiry_Answer_Content,replied_date)
VALUES 
('kim@example.com', '챗봇 안내 오류','챗봇이 학사일정을 제대로 안내하지 않아요.',  SYSDATE,'N',
   NUll,NUll,NUll);

 INSERT INTO chat_Inquiry(inquiry_email,inquiry_title, inquiry_message,inquiry_created_at,inquiry_answered,
admin_id, inquiry_Answer_Content,replied_date)
VALUES 
('Hong@example.com', '지갑분실물','오늘 지갑 분실물 있나요?? 하얀색지갑이고 안에 학생증있어요.',SYSDATE,'Y'
,'admin','00과 사무실에 분실물 있어요~ 확인해보세요',SYSDATE);

/*
-- 문의에 대한 답변
INSERT INTO chat_Inquiry_Answer(inquiry_id, admin_id, answer_content, replied_date)
VALUES 
(2, 'admin_lee', '00과 사무실에 분실물 있어요~ 확인해보세요', SYSDATE);
*/

-- 챗봇 메인 메뉴
INSERT INTO chat_Main_Menu(menu_name, menu_link, menu_display_order)
VALUES 
('공지사항', '/notices', 1);
 INSERT INTO chat_Main_Menu(menu_name, menu_link, menu_display_order)
VALUES 
('성적조회', '/grade-info', 2);
 INSERT INTO chat_Main_Menu(menu_name, menu_link, menu_display_order)
VALUES 
('강의관리', '/lectures', 3);
 INSERT INTO chat_Main_Menu(menu_name, menu_link, menu_display_order)
VALUES 
('게시판', '/posts', 4);
 INSERT INTO chat_Main_Menu(menu_name, menu_link, menu_display_order)
VALUES 
('식당메뉴', '/restaurants', 5);
 INSERT INTO chat_Main_Menu(menu_name, menu_link, menu_display_order)
VALUES 
('지도', '/map', 6);