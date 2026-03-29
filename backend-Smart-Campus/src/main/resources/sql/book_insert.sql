INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (1, '혼자공부하는자바', '혼공자', '167283', '언어', '한빛미디어', 2021, 2, '/images/covers/book_img1.jpg');

INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (2, '혼자공부하는SQL', '혼공자', '123534', '언어', '한빛미디어', 2021, 1, '/images/covers/book_img2.jpg');

INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (3, '하나님, 그래서 그러셨군요!', '신애라', '267283', '철학·종교', '규장', 2013, 1, '/images/covers/book_img3.jpg');

INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (4, '종교 사회학', '김종서', '264533', '철학·종교', '서울대학교출판문화원', 1994, 2, '/images/covers/book_img4.jpg');

INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (5, '광기의 역사', '미셀 푸코', '345283', '문학·역사', '나남', 2012, 1, '/images/covers/book_img5.jpg');

INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (6, '모두를 위한 사회과학', '김윤태', '417283', '문학·역사', 'Humanist', 2025, 1, '/images/covers/book_img6.jpg');

INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (7, '첨단기술의 과학', '황정아, 채연석, 김민수 등', '412343', '과학', '해피사이언스-반니', 2021, 2, '/images/covers/book_img7.jpg');

INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (8, '미래 과학', '제단법인 카오스', '477283', '과학', '해피사이언스-반니', 2001, 1, '/images/covers/book_img8.jpg');

INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (9, '명작 스마트 소설', '프란츠 카프카, 나쓰메 소세키 등', '511111', '문학·역사', '문학나무', 2000, 1, '/images/covers/book_img9.jpg');

INSERT INTO book(book_id, book_title, book_author, book_isbn, book_category, book_publisher, book_publisher_year, book_stock, book_img) 
VALUES (10, '책의 역사', '다카미야 도사유키', '567344', '문학·역사', 'AK', 2002, 1, '/images/covers/book_img10.jpg');

INSERT INTO borrow(borrow_id, borrow_date, expected_return_date, return_date, borrow_status, userinfo_id, book_id)
VALUES (1, TO_DATE('2025-07-10', 'YYYY-MM-DD'), TO_DATE('2025-07-15', 'YYYY-MM-DD'), NULL, '대출중', 'userb', 1);

INSERT INTO borrow(borrow_id, borrow_date, expected_return_date, return_date, borrow_status, userinfo_id, book_id)
VALUES (2, TO_DATE('2025-07-10', 'YYYY-MM-DD'), TO_DATE('2025-07-15', 'YYYY-MM-DD'), TO_DATE('2025-07-14', 'YYYY-MM-DD'), '대출가능', 'userb', 2);