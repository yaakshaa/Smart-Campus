INSERT INTO study_room(study_room_id, room_name, max_people) 
VALUES (1, '제1스터디룸', 5);

INSERT INTO reservation(reservation_id, reservation_date, start_time, end_time, reservation_status, userinfo_id, study_room_id)
VALUES (1, TO_DATE('2025-07-10', 'YYYY-MM-DD'), TO_TIMESTAMP('10:00', 'HH24:MI'), TO_TIMESTAMP('12:00', 'HH24:MI'), '예약중', 'userb', 1);