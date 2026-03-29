package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.CommentLikeDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.service.CommentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment-like")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @Operation(summary = "댓글 좋아요 추가")
    @PostMapping
    public ResponseEntity<Response> createCommentLike(
            @RequestBody CommentLikeDTO commentLikeDTO,
            HttpSession session) {

        // ✅ 세션에서 로그인 유저 꺼내기 (PostController 방식)
        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "로그인 정보가 없습니다. 다시 로그인해주세요.",
                            null));
        }

        String userId = loginUser.getUserId();

        // ✅ 필수 파라미터 검증
        if (commentLikeDTO.getCommentNo() == null || userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Response(400, "댓글 번호 또는 사용자 정보가 없습니다.", null));
        }

        try {
            // ✅ userId 주입
            commentLikeDTO.setUserId(userId);

            // ✅ 좋아요 처리
            CommentLikeDTO created = commentLikeService.createCommentLike(commentLikeDTO);

            Response response = new Response();
            response.setStatus(ResponseStatusCode.CREATED_COMMENT_LIKE);
            response.setMessage(ResponseMessage.CREATED_COMMENT_LIKE);
            response.setData(created);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // ❗ 예상치 못한 예외 로깅
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response(500, "댓글 좋아요 처리 중 오류가 발생했습니다.", null));
        }
    }

    @Operation(summary = "댓글의 좋아요 수 조회")
    @GetMapping("/count/{commentNo}")
    public ResponseEntity<Response> getCommentLikeCount(@PathVariable Long commentNo) {
        int count = commentLikeService.countCommentLikesByCommentNo(commentNo);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.READ_COMMENT_LIKE);
        response.setMessage(ResponseMessage.READ_COMMENT_LIKE);
        response.setData(count);

        return ResponseEntity.ok(response);
    }
}
