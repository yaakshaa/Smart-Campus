package com.itwill.backend_smart_campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.StudyRoomDTO;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.service.StudyRoomService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/studyrooms")
@RequiredArgsConstructor
public class StudyRoomController {
    private final StudyRoomService studyRoomService;

    @Operation(summary = "스터디룸등록(관리자만 가능)")
    @PostMapping
    public ResponseEntity<Response> createStudyRoom(@RequestBody StudyRoomDTO studyRoomDTO, HttpSession session) throws Exception {
        String userId = (String) session.getAttribute("sUserInfo");
        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        int studyRoomId = studyRoomService.insertStudyRoom(studyRoomDTO, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATED_STUDYROOM);
        response.setMessage(ResponseMessage.CREATED_STUDYROOM);
        response.setData(studyRoomId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "스터디룸수정(관리자만 가능)")
    @PutMapping
    public ResponseEntity<Response> updateStudyRoom(@RequestBody StudyRoomDTO studyRoomDTO, HttpSession session) throws Exception {
        String userId = (String) session.getAttribute("sUserInfo");
        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        int studyRoomId = studyRoomService.updateStudyRoom(studyRoomDTO, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.UPDATED_STUDYROOM);
        response.setMessage(ResponseMessage.UPDATED_STUDYROOM);
        response.setData(studyRoomId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "스터디룸삭제(관리자만 가능)")
    @DeleteMapping("/{studyRoomId}")
    public ResponseEntity<Response> deleteStudyRoomById(@PathVariable int studyRoomId, HttpSession session) throws Exception {
        String userId = (String) session.getAttribute("sUserInfo");
        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        int deleteId = studyRoomService.deleteStudyRoomById(studyRoomId, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.DELETED_STUDYROOM);
        response.setMessage(ResponseMessage.DELETED_STUDYROOM);
        response.setData(deleteId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "전체스터디룸조회")
    @GetMapping
    public ResponseEntity<Response> getAllStudyRooms() throws Exception {
        List<StudyRoomDTO> studyRooms = studyRoomService.findAllStudyRooms();

        Response response = new Response();
        response.setStatus(ResponseStatusCode.STUDYROOM_SUCCESS);
        response.setMessage(ResponseMessage.STUDYROOM_SUCCESS);
        response.setData(studyRooms);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "스터디룸ID로 조회")
    @GetMapping("/{studyRoomId}")
    public ResponseEntity<Response> getStudyRoomById(@PathVariable int studyRoomId) throws Exception {
        StudyRoomDTO studyRoomDTO = studyRoomService.findStudyRoomById(studyRoomId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.STUDYROOM_SUCCESS);
        response.setMessage(ResponseMessage.STUDYROOM_SUCCESS);
        response.setData(studyRoomDTO);

        return ResponseEntity.ok(response);
    }

}
