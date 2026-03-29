INSERT INTO userinfo (userinfo_id, userinfo_password, userinfo_name, userinfo_email, userinfo_type)
VALUES ('admin01', 'admin01', '관리자', 'admin@gmail.com', 'ADMIN');

INSERT INTO admin (admin_id, userinfo_id, admin_date, admin_position)
VALUES ('admin01', 'admin01', SYSDATE, '교직원');