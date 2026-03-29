package com.itwill.backend_smart_campus.controller;

public class ResponseMessage {

    // 유저관련 응답 메시지
    public static final String LOGIN_SUCCESS_USER = "로그인 성공";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String LOGIN_CHECK_SUCCESS_USER = "로그인 한 상태";
    public static final String USER_JOIN_SUCCESS = "회원가입 성공";
    public static final String READ_USER = "회원 정보 읽기 성공";
    public static final String LOGOUT_USER ="로그아웃";

    public static final String CREATE_FAIL_EXISTED_USER = "아이디 중복";
    public static final String LOGIN_FAIL_NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATE_FAIL_EXISTED_PASSWORD = "비밀번호 중복";
    public static final String LOGIN_CHECK_FAIL_USER = "로그인한 상태가 아닙니다";
    public static final String UNAUTHORIZED_USER = "인증되지 않은 유저";
    public static final String UNKNOWN_EXCEPTION = "예상치 못한 예외 발생";
    public static final String CREATE_FAIL_EXISTED_EMAIL = "이메일 중복";
    public static final String LOGIN_FAIL_PASSWORD = "비밀번호가 틀렸습니다";

    // 공지사항 관련 응답 메시지
    public static final String CREATED_NOTICE = "공지사항 등록 성공";
    public static final String UPDATE_NOTICE = "공지사항 수정 성공";
    public static final String DELETE_NOTICE = "공지사항 삭제 성공";

    public static final String READ_NOTICE = "공지사항 상세 조회 성공";
    public static final String READ_ALL_NOTICE = "공지사항 전체 목록 조회 성공";

    public static final String FORBIDDEN_NOTICE = "관리자만 접근할 수 있습니다.";

    // 카테고리 관련 응답 메시지
    public static final String CREATED_CATEGORY = "카테고리 등록 성공";
    public static final String READ_ALL_CATEGORY = "카테고리 전체 조회 성공";
    public static final String READ_CATEGORY = "카테고리 상세 조회 성공";
    public static final String UPDATE_CATEGORY = "카테고리 이름 수정 성공";
    public static final String DELETE_CATEGORY = "카테고리 삭제 성공";

    // 게시글 관련 메시지
    public static final String CREATED_POST = "게시글 등록 성공";
    public static final String READ_ALL_POST = "게시글 전체 조회 성공";
    public static final String READ_POST = "게시글 상세 조회 성공";
    public static final String READ_POST_FAIL="게시글 조회 실패";
    public static final String UPDATE_POST = "게시글 수정 성공";
    public static final String DELETE_POST = "게시글 삭제 성공";

    // 게시글 좋아요 관련 메시지
    public static final String CREATED_POST_LIKE = "게시글 좋아요 추가 성공";
    public static final String DELETED_POST_LIKE = "게시글 좋아요 취소 성공";
    public static final String READ_POST_LIKE = "게시글 좋아요 수 조회 성공";

    // 댓글 관련 메시지
    public static final String CREATED_COMMENT = "댓글 등록 성공";
    public static final String UPDATED_COMMENT = "댓글 수정 성공";
    public static final String DELETED_COMMENT = "댓글 삭제 성공";
    public static final String READ_COMMENTS = "댓글 조회 성공";

    // 댓글 좋아요 관련 메시지
    public static final String CREATED_COMMENT_LIKE = "댓글 좋아요 추가 성공";
    public static final String DELETED_COMMENT_LIKE = "댓글 좋아요 취소 성공";
    public static final String READ_COMMENT_LIKE = "댓글 좋아요 수 조회 성공";

    // 도서 관련 메시지
    public static final String BOOK_SUCCESS = "요청이 성공적으로 처리되었습니다.";
    public static final String CREATED_BOOK = "도서가 등록되었습니다.";
    public static final String UPDATED_BOOK = "도서 정보가 수정되었습니다.";
    public static final String DELETED_BOOK = "도서 정보가 삭제되었습니다.";

    // 대출 관련 메시지
    public static final String BORROW_SUCCESS = "요청이 성공적으로 처리되었습니다.";
    public static final String CREATED_BORROW = "책을 대출하였습니다.";
    public static final String RETURNED_BORROW = "책을 반납하였습니다.";

     // 스터디룸 관련 상태 코드
    public static final String STUDYROOM_SUCCESS = "요청이 성공적으로 처리되었습니다.";
    public static final String CREATED_STUDYROOM = "스터디룸이 등록되었습니다.";
    public static final String UPDATED_STUDYROOM = "스터디룸 정보가 수정되었습니다.";
    public static final String DELETED_STUDYROOM = "스터디룸 정보가 삭제되었습니다.";

    // 예약 관련 상태 코드
    public static final String RESERVATION_SUCCESS = "요청이 성공적으로 처리되었습니다.";
    public static final String CREATED_RESERVATION = "스터디룸 예약이 되었습니다.";
    public static final String CANCEL_RESERVATION = "스터디룸 예약이 취소되었습니다.";

}
