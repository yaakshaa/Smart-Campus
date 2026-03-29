package com.itwill.backend_smart_campus.controller;

public class ResponseStatusCode {

    // 유저관련 상태 코드
    public static final int LOGIN_SUCCESS_USER = 1001;
    public static final int CREATED_USER = 1002;
    public static final int UPDATE_USER = 1003;
    public static final int DELETE_USER = 1004;
    public static final int LOGIN_CHECK_SUCCESS_USER = 1005;
    public static final int USER_JOIN_SUCCESS = 1006;
    public static final int READ_USER = 1007;
    public static final int LOGOUT_USER = 1008;

    public static final int CREATE_FAIL_EXISTED_USER = 1009;
    public static final int LOGIN_FAIL_NOT_FOUND_USER = 1010;
    public static final int CREATE_FAIL_EXISTED_PASSWORD = 1011;
    public static final int LOGIN_CHECK_FAIL_USER = 1012;
    public static final int UNAUTHORIZED_USER = 1013;
    public static final int UNKNOWN_EXCEPTION = 1014;
    public static final int CREATE_FAIL_EXISTED_EMAIL = 1015;
    public static final int LOGIN_FAIL_PASSWORD = 1016;

    // 공지사항 관련 상태 코드
    public static final int CREATED_NOTICE = 2001;
    public static final int UPDATE_NOTICE = 2002;
    public static final int DELETE_NOTICE = 2003;

    public static final int READ_NOTICE = 2004;
    public static final int READ_ALL_NOTICE = 2005;

    public static final int FORBIDDEN_NOTICE = 2403;

    // 카테고리 관련 코드
    public static final int CREATED_CATEGORY = 3101;
    public static final int READ_ALL_CATEGORY = 3102;
    public static final int READ_CATEGORY = 3103;
    public static final int UPDATE_CATEGORY = 3104;
    public static final int DELETE_CATEGORY = 3105;

    // 게시글 관련 상태 코드
    public static final int CREATED_POST = 3201;
    public static final int READ_ALL_POST = 3202;
    public static final int READ_POST = 3203;
    public static final int READ_POST_FAIL = 3204;
    public static final int UPDATE_POST = 3205;
    public static final int DELETE_POST = 3206;

    // 게시글 좋아요 관련 상태 코드
    public static final int CREATED_POST_LIKE = 3301;
    public static final int DELETED_POST_LIKE = 3302;
    public static final int READ_POST_LIKE = 3303;

    // 댓글 관련 상태 코드
    public static final int CREATED_COMMENT = 3401;
    public static final int UPDATED_COMMENT = 3402;
    public static final int DELETED_COMMENT = 3403;
    public static final int READ_COMMENTS = 3404;
    
    // 댓글 좋아요 관련 상태 코드
    public static final int CREATED_COMMENT_LIKE = 3501;
    public static final int DELETED_COMMENT_LIKE = 3502;
    public static final int READ_COMMENT_LIKE = 3503;

    // 도서 관련 상태 코드
    public static final int BOOK_SUCCESS = 4001;
    public static final int CREATED_BOOK = 4002;
    public static final int UPDATED_BOOK = 4003;
    public static final int DELETED_BOOK = 4004;

    // 대출 관련 상태 코드
    public static final int BORROW_SUCCESS = 4101;
    public static final int CREATED_BORROW = 4102;
    public static final int RETURNED_BORROW = 4103;
    public static final int BORROW_LIMIT_EXCEEDED = 4104;

    // 스터디룸 관련 상태 코드
    public static final int STUDYROOM_SUCCESS = 4201;
    public static final int CREATED_STUDYROOM = 4202;
    public static final int UPDATED_STUDYROOM = 4202;
    public static final int DELETED_STUDYROOM = 4202;

    // 예약 관련 상태 코드
    public static final int RESERVATION_SUCCESS = 4301;
    public static final int CREATED_RESERVATION = 4302;
    public static final int CANCEL_RESERVATION = 4302;
    

}
