package com.itwill.backend_smart_campus.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.ChatDistractorDTO;
import com.itwill.backend_smart_campus.dto.ChatInquiryAnswerDTO;
import com.itwill.backend_smart_campus.dto.ChatInquiryDTO;
import com.itwill.backend_smart_campus.dto.ChatMainMenuDTO;
import com.itwill.backend_smart_campus.dto.ChatQuestionDTO;
import com.itwill.backend_smart_campus.entity.ChatInquiry;
import com.itwill.backend_smart_campus.entity.ChatInquiryAnswer;

import com.itwill.backend_smart_campus.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/Chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "DB에 있는 기본 메뉴들 가져와서 화면에 보여주기")
    @GetMapping("/ChatMainMenu")
    public ResponseEntity<List<ChatMainMenuDTO>> getMainMenu() {
        List<ChatMainMenuDTO> menus = chatService.findMainMenus();
        return ResponseEntity.ok(menus);
    }

    @Operation(summary = "간단한 질문 채팅하기")
    @PostMapping("/ChatQuestion")
    public ResponseEntity<List<ChatQuestionDTO>> getDistractors(@RequestBody ChatQuestionDTO ChatQuestionDTO) {
        String chatContent = ChatQuestionDTO.getQuestionContent();
        List<ChatQuestionDTO> questionreturn = chatService.getDistractors(chatContent);
        return ResponseEntity.ok(questionreturn);
    }

    
    @Operation(summary = "질문가져오기")
    @GetMapping("/ChatQuestion")
    public ResponseEntity<List<ChatQuestionDTO>> getChatQuestion() {
    List<ChatQuestionDTO> menus = chatService.getChatQuestionAll();
    System.out.println(menus.get(0).getQuestionContent());
    return ResponseEntity.ok(menus);
    }

    @Operation(summary = "질문에 따라 연결된 선택지 리스트 응답")
    @GetMapping("/ChatQuestion/ChatDistractors/{questionId}")
    public ResponseEntity<List<ChatDistractorDTO>> getDistractors(@PathVariable("questionId") Integer chatId) {
        List<ChatDistractorDTO> distractorReturn = chatService.getDistractorsById(chatId);
        return ResponseEntity.ok(distractorReturn);
    }

    @Operation(summary = "관리자에게 문의하기")
    @PostMapping("/ChatInquiry")
    public ResponseEntity<Void> createInquiry(@RequestBody ChatInquiryDTO dto) {
        chatService.createInquiry(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "전체 문의 가져오기")
    @GetMapping("/ChatInquiry")
    public ResponseEntity<List<ChatInquiryDTO>> getInquiry() {
        List<ChatInquiryDTO> menus = chatService.getInquiry();
        return ResponseEntity.ok(menus);
    }

    @Operation(summary = "문의 디테일 불러오기")
    @GetMapping("/ChatAdminList/{inqueryId}")
    public ResponseEntity<List<ChatInquiryDTO>> getInqueryDetail(@PathVariable("inqueryId") Integer inqueryId) {
        List<ChatInquiryDTO> getInquireReturn = chatService.getChatInquirybyId(inqueryId);
        return ResponseEntity.ok(getInquireReturn);
    }

    @Operation(summary = "문의답변하기")
    @PostMapping("/ChatAdminList/Answer")
    public ResponseEntity<Void> setChatInquiryAnswer(@RequestBody ChatInquiryDTO chatInquiryDTO) {
        chatService.setChatInquiryAnswer(chatInquiryDTO);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "전체 선택지 리스트 반환 (질문 내용 포함)")
    @GetMapping("/ChatDistractor")
    public ResponseEntity<List<ChatDistractorDTO>> getAllDistractors() {
        List<ChatDistractorDTO> distractorList = chatService.getAllDistractors();
        return ResponseEntity.ok(distractorList);
    }

    @Operation(summary = "일부 선택지 리스트 반환 (질문 내용 포함)")
    @GetMapping("/ChatDistractor/{distractorId}")
    public ResponseEntity<List<ChatDistractorDTO>> getDistractorDetail(@PathVariable("distractorId") Integer distractorId) {
   
        List<ChatDistractorDTO> distractors = chatService.getChatDistractorbyId(distractorId);
        return ResponseEntity.ok(distractors);
    }

    @Operation(summary = "새로운 선택지 저장")
    @PostMapping("/ChatDistractor/save")
    public ResponseEntity<Void> createDistractor(@RequestBody ChatDistractorDTO dto) {
        chatService.saveDistractor(dto);
        return ResponseEntity.ok().build();
    }

    // 수정
    @Operation(summary = "선택지 수정")
    @PutMapping("/ChatDistractor")
    public ResponseEntity<Void> updateDistractor(@RequestBody ChatDistractorDTO dto) {
        chatService.updateDistractor(dto);
        return ResponseEntity.ok().build();
    }

    // 삭제
    @Operation(summary = "선택지 삭제")
    @DeleteMapping("/ChatDistractor/{distractorId}")
    public ResponseEntity<Void> deleteDistractor(@PathVariable Integer distractorId) {
        chatService.deleteDistractor(distractorId);
        return ResponseEntity.ok().build();
    }

    

}
