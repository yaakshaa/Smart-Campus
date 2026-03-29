package com.itwill.backend_smart_campus.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.BorrowDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.service.BorrowService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/borrows")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;

    @Operation(summary = "도서대출(학생만 가능)")
    @PostMapping
    public ResponseEntity<Response> borrowBook(@RequestBody Map<String, Integer> body, HttpSession session) throws Exception{
        int bookId = body.get("bookId");

        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String userId = loginUser != null ? loginUser.getUserId() : null;

        if(userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Response(ResponseStatusCode.LOGIN_CHECK_FAIL_USER,
                            ResponseMessage.LOGIN_CHECK_FAIL_USER,
                            null));
        }

        int borrowId = borrowService.borrowBook(bookId, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATED_BORROW);
        response.setMessage(ResponseMessage.CREATED_BORROW);
        response.setData(borrowId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "도서반납(학생만 가능)")
    @PutMapping("/return/{borrowId}")
    public ResponseEntity<Response> returnBook(@PathVariable int borrowId, HttpSession session) throws Exception {
         UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String userId = loginUser != null ? loginUser.getUserId() : null;

        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        int returnedId = borrowService.returnBook(borrowId, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.RETURNED_BORROW);
        response.setMessage(ResponseMessage.RETURNED_BORROW);
        response.setData(returnedId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "전체대출조회(관리자만 가능)")
    @GetMapping
    public ResponseEntity<Response> getAllBorrows(HttpSession session) throws Exception {
         UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String userId = loginUser != null ? loginUser.getUserId() : null;

        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        List<BorrowDTO> borrows = borrowService.findAllBorrows(userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.BORROW_SUCCESS);
        response.setMessage(ResponseMessage.BORROW_SUCCESS);
        response.setData(borrows);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "특정대출정보조회(관리자만 가능)")
    @GetMapping("/{borrowId}")
    public ResponseEntity<Response> getBorrowById(@PathVariable int borrowId, HttpSession session) throws Exception {
        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String userId = loginUser != null ? loginUser.getUserId() : null;
        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        } 

        BorrowDTO borrowDTO = borrowService.findBorrowById(borrowId, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.BORROW_SUCCESS);
        response.setMessage(ResponseMessage.BORROW_SUCCESS);
        response.setData(borrowDTO);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자별대출목록조회(본인 혹은 관리자만 가능")
    @GetMapping("/user/{targetUserId}")
    public ResponseEntity<Response> getBorrowsByUserId(@PathVariable String targetUserId, HttpSession session) throws Exception {
        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String requesterUserId = loginUser != null ? loginUser.getUserId() : null;
        
        if(requesterUserId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        } 

        List<BorrowDTO> borrows = borrowService.findBorrowsByUserId(targetUserId, requesterUserId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.BORROW_SUCCESS);
        response.setMessage(ResponseMessage.BORROW_SUCCESS);
        response.setData(borrows);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "미반납도서목록조회(관리자만 가능)")
    @GetMapping("/unreturned")
    public ResponseEntity<Response> getNoReturnedBorrows(HttpSession session) throws Exception {
         UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        String userId = loginUser != null ? loginUser.getUserId() : null;
        
        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        } 

        List<BorrowDTO> borrowDTO = borrowService.findNoReturnedBorrows(userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.BORROW_SUCCESS);
        response.setMessage(ResponseMessage.BORROW_SUCCESS);
        response.setData(borrowDTO);

        return ResponseEntity.ok(response);
    }
}
