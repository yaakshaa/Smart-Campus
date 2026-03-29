package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.PostLikeDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.entity.Post;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.service.PostLikeService;
import com.itwill.backend_smart_campus.service.PostService;
import com.itwill.backend_smart_campus.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postlike")
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final PostService postService;
    private final UserInfoService userInfoService;

    @Operation(summary = "게시글 좋아요 수 조회")
    @GetMapping("/count/{postNo}")
    public ResponseEntity<Response> countPostLikes(@PathVariable Long postNo) {
        int count = postLikeService.countPostLikesByPostNo(postNo);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.READ_POST_LIKE);
        response.setMessage(ResponseMessage.READ_POST_LIKE);
        response.setData(count);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 좋아요 추가 (중복 방지)")
    @PostMapping("/{postNo}")
    public ResponseEntity<Response> createPostLike(
            @PathVariable Long postNo,
            HttpSession session) {

        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "로그인 정보가 없습니다. 다시 로그인 해주세요.",
                            null));
        }

        String userId = loginUser.getUserId();

        UserInfo user = userInfoService.findUserInfo(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "회원을 찾을 수 없습니다. 세션 정보: " + userId,
                            null));
        }

        Post post = postService.findPostEntity(postNo);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Response(ResponseStatusCode.READ_POST_FAIL,
                            "게시글이 존재하지 않습니다.",
                            null));
        }

        if (postLikeService.existsByPostAndUserInfo(post, user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new Response(ResponseStatusCode.CREATED_POST_LIKE,
                            "이미 좋아요를 누르셨습니다.",
                            null));
        }

        PostLikeDTO dto = new PostLikeDTO();
        dto.setPostNo(postNo);
        dto.setUserId(userId);

        PostLikeDTO created = postLikeService.createPostLike(dto);

        postService.incrementPostLikeCount(post);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATED_POST_LIKE);
        response.setMessage(ResponseMessage.CREATED_POST_LIKE);
        response.setData(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
