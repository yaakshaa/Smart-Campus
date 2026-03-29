package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.NoticeDTO;
import com.itwill.backend_smart_campus.dto.NoticeRequestDTO;
import com.itwill.backend_smart_campus.dto.UserInfoResponseDto;
import com.itwill.backend_smart_campus.service.NoticeService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // ✅ 관리자 권한 확인 (UserInfoResponseDto 기준)
    private boolean isAdmin(HttpSession session) {
        UserInfoResponseDto user = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    // ✅ 공지 등록 (관리자 전용)
    @Operation(summary = "공지 등록 (관리자 전용)")
    @PostMapping("/admin")
    public ResponseEntity<Response> createNotice(
            @RequestPart NoticeRequestDTO requestDTO,
            @RequestPart(required = false) MultipartFile[] files,
            HttpSession session) {

        UserInfoResponseDto user = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response(ResponseStatusCode.FORBIDDEN_NOTICE, ResponseMessage.FORBIDDEN_NOTICE, null));
        }

        requestDTO.setAdminId(user.getUserId());
        noticeService.createNotice(requestDTO, files);

        return ResponseEntity.ok(new Response(
                ResponseStatusCode.CREATED_NOTICE,
                ResponseMessage.CREATED_NOTICE,
                null));
    }

    // ✅ 공지 수정 (관리자 전용)
    @Operation(summary = "공지 수정 (관리자 전용)")
    @PutMapping("/admin/{noticeNo}")
    public ResponseEntity<Response> updateNotice(
            @PathVariable Long noticeNo,
            @RequestPart NoticeRequestDTO requestDTO,
            @RequestPart(required = false) MultipartFile[] addFiles,
            HttpSession session) {

        UserInfoResponseDto user = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response(ResponseStatusCode.FORBIDDEN_NOTICE, ResponseMessage.FORBIDDEN_NOTICE, null));
        }

        requestDTO.setNoticeNo(noticeNo);
        requestDTO.setAdminId(user.getUserId());

        noticeService.updateNotice(requestDTO, addFiles);

        return ResponseEntity.ok(new Response(
                ResponseStatusCode.UPDATE_NOTICE,
                ResponseMessage.UPDATE_NOTICE,
                null));
    }

    // ✅ 공지 삭제 (관리자 전용)
    @Operation(summary = "공지 삭제 (관리자 전용)")
    @DeleteMapping("/admin/{noticeNo}")
    public ResponseEntity<Response> deleteNotice(
            @PathVariable Long noticeNo,
            HttpSession session) {

        UserInfoResponseDto user = (UserInfoResponseDto) session.getAttribute("sUserInfo");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response(ResponseStatusCode.FORBIDDEN_NOTICE, ResponseMessage.FORBIDDEN_NOTICE, null));
        }

        noticeService.deleteNotice(noticeNo);

        return ResponseEntity.ok(new Response(
                ResponseStatusCode.DELETE_NOTICE,
                ResponseMessage.DELETE_NOTICE,
                null));
    }

    // ✅ 공지사항 전체 목록 조회
    @Operation(summary = "공지사항 전체 목록 조회")
    @GetMapping
    public ResponseEntity<Response> getNoticeList() {
        List<NoticeDTO> list = noticeService.getNoticeList();
        return ResponseEntity.ok(new Response(
                ResponseStatusCode.READ_ALL_NOTICE,
                ResponseMessage.READ_ALL_NOTICE,
                list));
    }

    // ✅ 공지사항 상세 조회
    @Operation(summary = "공지사항 상세 조회")
    @GetMapping("/{noticeNo}")
    public ResponseEntity<Response> getNoticeById(@PathVariable Long noticeNo) {
        NoticeDTO dto = noticeService.getNoticeById(noticeNo);
        return ResponseEntity.ok(new Response(
                ResponseStatusCode.READ_NOTICE,
                ResponseMessage.READ_NOTICE,
                dto));
    }
}