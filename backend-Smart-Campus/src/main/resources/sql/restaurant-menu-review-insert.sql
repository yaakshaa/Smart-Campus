INSERT INTO restaurant (restaurant_id, restaurant_name, restaurant_location)
VALUES (1, '교직원식당', 'A건물 3층');

INSERT INTO restaurant (restaurant_id, restaurant_name, restaurant_location)
VALUES (2, '학생식당', 'B건물 3층');


-- 교직원식당 (restaurant_id = 1)
INSERT INTO menu (menu_id, restaurant_id, menu_date, meal_type, food_name, allergy_info, calorie)
VALUES (1, 1, TO_DATE('2025-07-21', 'YYYY-MM-DD'), '아침', '계란찜', '달걀', 350);

INSERT INTO menu (menu_id, restaurant_id, menu_date, meal_type, food_name, allergy_info, calorie)
VALUES (2, 1, TO_DATE('2025-07-21', 'YYYY-MM-DD'), '점심', '돈까스', '밀가루, 돼지고기', 800);

INSERT INTO menu (menu_id, restaurant_id, menu_date, meal_type, food_name, allergy_info, calorie)
VALUES (3, 1, TO_DATE('2025-07-21', 'YYYY-MM-DD'), '저녁', '김치찌개', '없음', 500);

-- 학생식당 (restaurant_id = 2)
INSERT INTO menu (menu_id, restaurant_id, menu_date, meal_type, food_name, allergy_info, calorie)
VALUES (4, 2, TO_DATE('2025-07-21', 'YYYY-MM-DD'), '아침', '토스트,우유', '달걀, 밀가루', 400);

INSERT INTO menu (menu_id, restaurant_id, menu_date, meal_type, food_name, allergy_info, calorie)
VALUES (5, 2, TO_DATE('2025-07-21', 'YYYY-MM-DD'), '점심', '제육덮밥,밥,상추', '돼지고기', 700);

INSERT INTO menu (menu_id, restaurant_id, menu_date, meal_type, food_name, allergy_info, calorie)
VALUES (6, 2, TO_DATE('2025-07-21', 'YYYY-MM-DD'), '저녁', '라면,김치,밥,단무지', '밀가루', 550);

INSERT INTO menu (menu_id, restaurant_id, menu_date, meal_type, food_name, allergy_info, calorie)
VALUES (7, 2, TO_DATE('2025-07-20', 'YYYY-MM-DD'), '아침', '토스트', '달걀, 밀가루', 400);


-- 교직원식당 점심 (menu_id = 2)
INSERT INTO review (review_id, userinfo_id, menu_id, rating, created_at, reviewcomment)
VALUES (1, 'userb', 2, 5, SYSTIMESTAMP, '맛있고 바삭했어요.');

INSERT INTO review (review_id, userinfo_id, menu_id, rating, created_at, reviewcomment)
VALUES (2, 'userb', 2, 4, SYSTIMESTAMP, '양이 조금 적었어요.');

INSERT INTO review (review_id, userinfo_id, menu_id, rating, created_at, reviewcomment)
VALUES (3, 'userb', 2, 5, SYSTIMESTAMP, '직원식당치고 퀄리티 굿!');

-- 학생식당 저녁 (menu_id = 6)
INSERT INTO review (review_id, userinfo_id, menu_id, rating, created_at, reviewcomment)
VALUES (4, 'userb', 6, 3, SYSTIMESTAMP, '기대보다 평범한 라면.');

INSERT INTO review (review_id, userinfo_id, menu_id, rating, created_at, reviewcomment)
VALUES (5, 'userb', 6, 2, SYSTIMESTAMP, '조금 싱거웠어요.');

INSERT INTO review (review_id, userinfo_id, menu_id, rating, created_at, reviewcomment)
VALUES (6, 'userb', 6, 4, SYSTIMESTAMP, '편하게 한 끼 때우기 좋아요.');

