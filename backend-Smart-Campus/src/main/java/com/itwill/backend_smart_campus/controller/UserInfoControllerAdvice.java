package com.itwill.backend_smart_campus.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.itwill.backend_smart_campus.exception.ExistedEmailException;
import com.itwill.backend_smart_campus.exception.ExistedPasswordException;
import com.itwill.backend_smart_campus.exception.ExistedUserException;
import com.itwill.backend_smart_campus.exception.PasswordMismatchException;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;

@RestControllerAdvice
public class UserInfoControllerAdvice {

    // 회원을 찾을 수 없음
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFoundException(UserNotFoundException ex) {
        Response response = new Response();
        response.setStatus(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER);
        response.setMessage(ResponseMessage.LOGIN_FAIL_NOT_FOUND_USER);
        response.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(response); // 중요! 200 OK로 내려줘야 프론트에서 처리 가능
    }

    // 비밀번호가 틀렸습니다
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Response> handlePasswordMismatchException(PasswordMismatchException ex) {
        Response response = new Response();
        response.setStatus(ResponseStatusCode.LOGIN_FAIL_PASSWORD);
        response.setMessage(ResponseMessage.LOGIN_FAIL_PASSWORD);
        response.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 아이디 중복
    @ExceptionHandler(ExistedUserException.class)
    public ResponseEntity<Response> handleExistedUserException(ExistedUserException ex) {
        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATE_FAIL_EXISTED_USER); // ex: "CREATE_FAIL_EXISTED_USER"
        response.setMessage(ResponseMessage.CREATE_FAIL_EXISTED_USER); // ex: "회원아이디중복"
        response.setData(null);
        return ResponseEntity.ok(response); // 중요! HTTP 200으로 반환해야 프론트가 catch 안 타고 정상 처리
    }

    // 비밀번호 중복
    @ExceptionHandler(ExistedPasswordException.class)
    public ResponseEntity<Response> handleExistedPasswordException(ExistedPasswordException ex) {
        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATE_FAIL_EXISTED_PASSWORD); // 예: "CREATE_FAIL_EXISTED_PASSWORD"
        response.setMessage(ResponseMessage.CREATE_FAIL_EXISTED_PASSWORD); // 예: "비밀번호 중복"
        response.setData(null);
        return ResponseEntity.ok(response); // 프론트 catch 안 타게 HTTP 200 반환
    }

    // 이메일 중복
    @ExceptionHandler(ExistedEmailException.class)
    public ResponseEntity<Response> handleDataIntegrityViolation(ExistedEmailException ex) {
        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATE_FAIL_EXISTED_EMAIL);
        response.setMessage(ResponseMessage.CREATE_FAIL_EXISTED_EMAIL); // 예: "회원아이디 또는 이메일이 중복됩니다."
        response.setData(null);
        return ResponseEntity.ok(response);
    }
}