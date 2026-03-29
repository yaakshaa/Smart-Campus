package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.PostDTO;
import com.itwill.backend_smart_campus.dto.UserInfoDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.entity.Category;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.service.CategoryService;
import com.itwill.backend_smart_campus.service.PostService;
import com.itwill.backend_smart_campus.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final UserInfoService userInfoService;
    private final CategoryService categoryService;

    @Operation(summary = "게시글 등록")
    @PostMapping
    public ResponseEntity<Response> createPost(
            @RequestBody PostDTO postDTO,
            HttpSession session) throws Exception {

        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "로그인 후 이용 가능합니다.", null));
        }

        UserInfo userInfo = userInfoService.findUserInfo(loginUser.getUserId());
        Category category = categoryService.findCategoryEntity(postDTO.getCategoryNo());

        PostDTO created = postService.createPost(postDTO, userInfo, category);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATED_POST);
        response.setMessage(ResponseMessage.CREATED_POST);
        response.setData(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "카테고리별 또는 전체 게시글 조회")
    @GetMapping
    public ResponseEntity<Response> findAllPosts(
            @RequestParam(name = "category", required = false) String category) {
        List<PostDTO> list;

        if (category == null || category.equals("전체")) {
            list = postService.findAllPosts();
        } else {
            list = postService.findPostsByCategoryName(category);
        }

        Response response = new Response();
        response.setStatus(ResponseStatusCode.READ_ALL_POST);
        response.setMessage(ResponseMessage.READ_ALL_POST);
        response.setData(list);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 조회 ")
    @GetMapping("/{postNo}")
    public ResponseEntity<Response> findPostById(@PathVariable Long postNo) {
        PostDTO dto = postService.findPostAndIncrementView(postNo);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.READ_POST);
        response.setMessage(ResponseMessage.READ_POST);
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{postNo}")
    public ResponseEntity<Response> updatePost(
            @PathVariable Long postNo,
            @RequestBody PostDTO postDTO,
            HttpSession session) {

        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "로그인 후 이용 가능합니다.", null));
        }

        PostDTO original = postService.findPostById(postNo);
        if (!original.getUserId().equals(loginUser.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "본인 게시글만 수정할 수 있습니다.", null));
        }

        PostDTO updated = postService.updatePost(postNo, postDTO);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.UPDATE_POST);
        response.setMessage(ResponseMessage.UPDATE_POST);
        response.setData(updated);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postNo}")
    public ResponseEntity<Response> deletePost(
            @PathVariable Long postNo,
            HttpSession session) {

        UserInfoResponseDto loginUser = (UserInfoResponseDto) session.getAttribute("sUserInfo");

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "로그인 후 이용 가능합니다.", null));
        }

        PostDTO original = postService.findPostById(postNo);
        if (original == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response(ResponseStatusCode.READ_POST_FAIL,
                            "게시글이 존재하지 않습니다.", null));
        }

        UserInfo userInfo = userInfoService.findUserInfo(loginUser.getUserId());
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "회원 정보를 찾을 수 없습니다.", null));
        }

        boolean isOwner = original.getUserId().equals(loginUser.getUserId());
        boolean isAdmin = "관리자".equals(userInfo.getUserType());

        if (!isOwner && !isAdmin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(ResponseStatusCode.LOGIN_FAIL_NOT_FOUND_USER,
                            "본인 또는 관리지만 삭제할 수 있습니다.", null));
        }

        postService.deletePost(postNo);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.DELETE_POST);
        response.setMessage(ResponseMessage.DELETE_POST);
        response.setData(postNo);

        return ResponseEntity.ok(response);
    }
}
