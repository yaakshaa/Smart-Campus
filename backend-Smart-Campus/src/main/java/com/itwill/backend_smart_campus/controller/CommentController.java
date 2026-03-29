package com.itwill.backend_smart_campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.itwill.backend_smart_campus.dto.CommentDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 등록")
    @PostMapping
    public ResponseEntity<Response> createComment(
            @RequestBody CommentDTO commentDTO,
            HttpSession session) {

        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            ResponseMessage.LOGIN_FAIL_NOT_FOUND_USER,
                            null));
        }

        commentDTO.setUserId(loginUser.getUserId());

        CommentDTO created = commentService.createComment(commentDTO);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATED_COMMENT);
        response.setMessage(ResponseMessage.CREATED_COMMENT);
        response.setData(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentNo}")
    public ResponseEntity<Response> updateComment(
            @PathVariable Long commentNo,
            @RequestBody CommentDTO commentDTO,
            HttpSession session) {

        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            ResponseMessage.LOGIN_FAIL_NOT_FOUND_USER,
                            null));
        }

        CommentDTO original = commentService.findCommentById(commentNo);
        if (!original.getUserId().equals(loginUser.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "본인 댓글만 수정할 수 있습니다", null));
        }

        commentDTO.setUserId(loginUser.getUserId()); // 혹시 모르니 다시 설정

        CommentDTO updated = commentService.updateComment(commentNo, commentDTO);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.UPDATED_COMMENT);
        response.setMessage(ResponseMessage.UPDATED_COMMENT);
        response.setData(updated);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentNo}")
    public ResponseEntity<Response> deleteComment(
            @PathVariable Long commentNo,
            HttpSession session) {

        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            ResponseMessage.LOGIN_FAIL_NOT_FOUND_USER,
                            null));
        }

        CommentDTO original = commentService.findCommentById(commentNo);
        if (!original.getUserId().equals(loginUser.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "본인 댓글만 삭제할 수 있습니다", null));
        }

        commentService.deleteComment(commentNo);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.DELETED_COMMENT);
        response.setMessage(ResponseMessage.DELETED_COMMENT);
        response.setData(commentNo);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 번호로 댓글 목록 조회")
    @GetMapping("/{postNo}")
    public ResponseEntity<Response> getCommentsByPostNo(@PathVariable Long postNo) {
        List<CommentDTO> comments = commentService.getCommentsByPostNo(postNo);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.READ_COMMENTS);
        response.setMessage(ResponseMessage.READ_COMMENTS);
        response.setData(comments);

        return ResponseEntity.ok(response);
    }
}
