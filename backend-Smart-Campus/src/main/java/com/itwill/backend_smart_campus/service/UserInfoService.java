package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.UserInfoDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.entity.UserInfo;

public interface UserInfoService {

    // 회원가입
    int create(UserInfoDTO dto) throws Exception;

    // 로그인
    UserInfoResponseDto login(String userId, String password) throws Exception;

    // 정보수정
    int update(UserInfoDTO dto) throws Exception;

    // 회원 삭제
    int remove(String userId) throws Exception;
    
    //유저 조회
    UserInfo findUserInfo(String userId);
}