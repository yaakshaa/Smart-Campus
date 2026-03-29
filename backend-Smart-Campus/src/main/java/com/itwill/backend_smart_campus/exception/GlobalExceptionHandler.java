package com.itwill.backend_smart_campus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.itwill.backend_smart_campus.controller.Response;
import com.itwill.backend_smart_campus.controller.ResponseStatusCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // // 대출 권수 초과 등
    // @ExceptionHandler(BorrowUserNotFoundException.class)
    // public ResponseEntity<Response> handleBorrowLimit(BorrowUserNotFoundException ex) {
    //     Response resp = new Response();
    //     resp.setStatus(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER);  // 예: 400
    //     resp.setMessage(ex.getMessage());
    //     resp.setData(null);
    //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    // }


    // 대출 권수 초과 등
    @ExceptionHandler(BorrowLimtExceededException.class)
    public ResponseEntity<Response> handleBorrowLimit(BorrowLimtExceededException ex) {
        Response resp = new Response();
        resp.setStatus(ResponseStatusCode.BORROW_LIMIT_EXCEEDED);  // 예: 400
        resp.setMessage(ex.getMessage());
        resp.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }
}
