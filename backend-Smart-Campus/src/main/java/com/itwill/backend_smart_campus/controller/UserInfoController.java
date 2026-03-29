package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.StudentDTO;
import com.itwill.backend_smart_campus.dto.UserInfoDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.entity.Student;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.exception.*;
import com.itwill.backend_smart_campus.service.StudentService;
import com.itwill.backend_smart_campus.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/userinfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StudentService studentService;

    @Operation(summary = "회원가입")
    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody UserInfoDTO dto)
            throws ExistedUserException, Exception {
        // 유저 생성
        userInfoService.create(dto);

        // ✅ 학생이라면 Student 테이블에 추가 등록
        if ("STUDENT".equalsIgnoreCase(dto.getUserType())) {
            studentService.createByUserId(dto.getUserId());
        }

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATED_USER);
        response.setMessage(ResponseMessage.CREATED_USER);
        response.setData(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(
            @RequestParam String userId,
            @RequestParam String password,
            HttpSession session)
            throws UserNotFoundException, PasswordMismatchException, Exception {

        UserInfoResponseDto userDto = userInfoService.login(userId, password);

        
        StudentDTO studentDto = null;
        
        // ✅ 학생이면 StudentDTO 생성 및 세션/응답에 추가
        if ("STUDENT".equalsIgnoreCase(userDto.getRole())) {
            Student student = studentService.findByUserId(userId);
            studentDto = StudentDTO.toDto(student);
            session.setAttribute("sStudentInfo", studentDto);
            // ✅ UserInfoResponseDto에 studentId 추가
            userDto.setStudentId(student.getStudentId());
        }
        // ✅ 세션 저장
        session.setAttribute("sUserInfo", userDto);
        
        // ✅ 응답 구성
        Response response = new Response();
        response.setStatus(ResponseStatusCode.LOGIN_SUCCESS_USER);
        response.setMessage(ResponseMessage.LOGIN_SUCCESS_USER);
        response.setData(userDto);

        if (studentDto != null) {
            response.setExtra("studentDto", studentDto); // ✅ 핵심!
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그인 상태 확인")
    @GetMapping("/loginCheck")
    public ResponseEntity<Response> loginCheck(HttpSession session) {
        UserInfoResponseDto userDto = (UserInfoResponseDto) session.getAttribute("sUserInfo");

        Response response = new Response();
        if (userDto != null) {
            response.setStatus(ResponseStatusCode.LOGIN_CHECK_SUCCESS_USER);
            response.setMessage(ResponseMessage.LOGIN_CHECK_SUCCESS_USER);
            response.setData(userDto); // 그대로 프론트 전달
        } else {
            response.setStatus(ResponseStatusCode.LOGIN_CHECK_FAIL_USER);
            response.setMessage(ResponseMessage.LOGIN_CHECK_FAIL_USER);
            response.setData(null);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃")
    @GetMapping("/logout")
    public ResponseEntity<Response> logoutUser(HttpSession session) {
        session.invalidate(); // 세션 제거
        Response response = new Response();
        response.setStatus(ResponseStatusCode.LOGOUT_USER);
        response.setMessage(ResponseMessage.LOGOUT_USER);
        response.setData(null);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 - 로그인한 사용자 정보 조회")
    @GetMapping("/userpage")
    public ResponseEntity<Response> getUserPage(HttpSession session) {
        UserInfoResponseDto userDto = (UserInfoResponseDto) session.getAttribute("sUserInfo");

        Response response = new Response();
        if (userDto != null) {
            response.setStatus(ResponseStatusCode.READ_USER);
            response.setMessage(ResponseMessage.READ_USER);
            response.setData(userDto); // 바로 전달
        } else {
            response.setStatus(ResponseStatusCode.LOGIN_CHECK_FAIL_USER);
            response.setMessage(ResponseMessage.LOGIN_CHECK_FAIL_USER);
            response.setData(null);
        }

        return ResponseEntity.ok(response);
    }

}