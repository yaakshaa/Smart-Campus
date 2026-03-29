package com.itwill.backend_smart_campus.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.backend_smart_campus.dto.UserInfoDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.entity.Admin;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.exception.ExistedEmailException;
import com.itwill.backend_smart_campus.exception.ExistedPasswordException;
import com.itwill.backend_smart_campus.exception.ExistedUserException;
import com.itwill.backend_smart_campus.exception.PasswordMismatchException;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.repository.AdminRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private final UserInfoRepository userInfoRepository;
    private final AdminRepository adminRepository;

    // 회원가입
    @Override
    public int create(UserInfoDTO userInfoDTO)
            throws ExistedUserException, ExistedPasswordException, ExistedEmailException {
        if (userInfoRepository.existsById(userInfoDTO.getUserId())) {
            throw new ExistedUserException("이미 존재하는 아이디입니다.");
        }

        if (userInfoRepository.existsByUserPassword(userInfoDTO.getUserPassword())) {
            throw new ExistedPasswordException("이미 사용 중인 비밀번호입니다.");
        }

        if (userInfoRepository.existsByUserEmail(userInfoDTO.getUserEmail())) {
            throw new ExistedEmailException("이미 사용 중인 이메일입니다.");
        }

        UserInfo userInfo = UserInfo.toEntity(userInfoDTO);
        userInfoRepository.save(userInfo);

        if ("ADMIN".equalsIgnoreCase(userInfoDTO.getUserType())) {
            Admin admin = Admin.builder()
                    .adminId(userInfoDTO.getUserId())
                    .userInfo(userInfo) // 이미 userInfo 엔티티는 위에서 저장했음
                    .adminDate(new java.sql.Date(System.currentTimeMillis())) // 현재 날짜로 저장
                    .adminPosition("관리자") // 기본 직위 (필요시 변경 가능)
                    .build();
            adminRepository.save(admin);
        }

        return 1; // 성공
    }

    // 회원로그인
    @Override
    public UserInfoResponseDto login(String userId, String password)
            throws UserNotFoundException, PasswordMismatchException {

        // 1. 사용자 조회
        UserInfo userInfo = userInfoRepository.findByUserId(userId);

        // 2. 존재 여부 확인
        if (userInfo == null) {
            throw new UserNotFoundException(userId + "는 존재하지 않는 아이디입니다.");
        }

        // 3. 비밀번호 일치 확인
        if (!userInfo.getUserPassword().equals(password)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        // 4. 로그인 성공 시 DTO 반환
        return UserInfoResponseDto.builder()
                .userId(userInfo.getUserId())
                .name(userInfo.getUserName())
                .email(userInfo.getUserEmail())
                .role(userInfo.getUserType())
                .build(); // studentId는 null로 초기화됨
    }

    // 회원수정
    @Override
    public int update(UserInfoDTO userDto) throws Exception {
        // DTO → Entity 변환 후 저장
        userInfoRepository.save(UserInfo.toEntity(userDto));
        return 0;
    }

    // 회원탈퇴
    @Override
    public int remove(String userId) throws Exception {
        userInfoRepository.deleteById(userId);
        return 0;
    }

    // 사용자 정보 조회
    @Override
    public UserInfo findUserInfo(String userId) {
        return userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
    }
}