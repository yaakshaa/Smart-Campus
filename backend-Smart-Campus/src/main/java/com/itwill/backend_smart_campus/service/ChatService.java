package com.itwill.backend_smart_campus.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.backend_smart_campus.dto.*;
import com.itwill.backend_smart_campus.entity.*;
import com.itwill.backend_smart_campus.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMainMenuRepository chatMainMenuRepository;
    private final ChatDistractorRepository chatDistractorRepository;
    private final ChatQuestionRepository chatQuestionRepository;
    private final ChatInquiryRepository chatInquiryRepository;
    private final ChatInquiryAnswerRepository chatInquiryAnswerRepository;

    // 메인 메뉴 가져오기
    public List<ChatMainMenuDTO> findMainMenus() {
        return chatMainMenuRepository.findAllByOrderByMenuDisplayOrder()
                .stream()
                .map(ChatMainMenuDTO::toDto)
                .collect(Collectors.toList());
    }

    // 간단한 질문 저장
    public List<ChatQuestionDTO> getDistractors(String chatContent) {

        List<ChatQuestionDTO> rtnChatQuestionDTO = new ArrayList<ChatQuestionDTO>();
        ChatQuestionDTO chatdto = new ChatQuestionDTO();
        List<ChatQuestion> listchatQuestion = chatQuestionRepository.findByQuestionContentLike("%" + chatContent + "%");
        int count = listchatQuestion.size();
        // 결과가 없을시
        if (count == 0) {
            return rtnChatQuestionDTO;
        }
        // 결과가 하나일때 distractor 리턴
        else if (count == 1) {
            List<ChatDistractor> listChatdistractor = chatDistractorRepository
                    .findByChatQuestion(listchatQuestion.get(0));
            List<ChatDistractorDTO> listChatdistractorDTO = listChatdistractor.stream()
                    .map(ChatDistractorDTO::toDto)
                    .collect(Collectors.toList());

            chatdto.setListChatDistractorDTO(listChatdistractorDTO);
            rtnChatQuestionDTO.add(chatdto);
            return rtnChatQuestionDTO;

        }
        // 결과가 하나이상이면 Question 리턴
        else {
            rtnChatQuestionDTO = listchatQuestion.stream()
                    .map(ChatQuestionDTO::toDto)
                    .collect(Collectors.toList());

            return rtnChatQuestionDTO;
        }
    }

    //question 가져오기 getChatQuestionAll
  public List<ChatQuestionDTO> getChatQuestionAll() {
        return chatQuestionRepository.findAll()
                .stream()
                .map(ChatQuestionDTO::toDto)
                .collect(Collectors.toList());
    }




    // 연관 질문지가 두개인경우
    // 질문에 따른 선택지 리스트 조회
    public List<ChatDistractorDTO> getDistractorsById(Integer questionId) {
        ChatQuestion chatQuestion = new ChatQuestion();
        chatQuestion.setQuestionId(questionId);

        List<ChatDistractor> distractors = chatDistractorRepository.findByChatQuestion(chatQuestion);
        List<ChatDistractorDTO> listChatdistractorDTO = distractors.stream()
                .map(ChatDistractorDTO::toDto)
                .collect(Collectors.toList());

        return listChatdistractorDTO;
    }

    // 문의 등록
    public void createInquiry(ChatInquiryDTO dto) {
        ChatInquiry entity = ChatInquiry.builder()
                .inquiryEmail(dto.getInquiryEmail())
                .inquiryTitle(dto.getInquiryTitle())
                .inquiryMessage(dto.getInquiryMessage())
                .inquiryAnswered("N") // 초기 상태는 N
                .build();

        chatInquiryRepository.save(entity);
    }

    // 문의 전체보기
    public List<ChatInquiryDTO> getInquiry() {
        return chatInquiryRepository.findAllByOrderByInquiryIdDesc()
                .stream()
                .map(ChatInquiryDTO::toDto)
                .collect(Collectors.toList());
    }

    // 문의내용 자세히 보기
    public List<ChatInquiryDTO> getChatInquirybyId(Integer inqueryId) {
        System.out.println("##########################################" + inqueryId);
        List<ChatInquiryDTO> InquiryDetailDTO = chatInquiryRepository.findByInquiryId(inqueryId)
                .stream()
                .map(ChatInquiryDTO::toDto)
                .collect(Collectors.toList());

        return InquiryDetailDTO;
    }

    // 답변하기
    public void setChatInquiryAnswer(ChatInquiryDTO chatInquiryDTO) {
        int id = chatInquiryDTO.getInquiryId();
        List<ChatInquiry> setChatInquiryAnswer = chatInquiryRepository.findByInquiryId(id);
        ChatInquiry entity = setChatInquiryAnswer.get(0);
        entity.setAdminId("admin");
        entity.setInquiryAnswerContent(chatInquiryDTO.getInquiryAnswerContent());
        entity.setInquiryAnswered("Y");
        entity.setRepliedDate(new Date());

        chatInquiryRepository.save(entity);
    }

    // 전체 QnA가져오기
    public List<ChatDistractorDTO> getAllDistractors() {
        List<ChatDistractor> distractors = chatDistractorRepository.findAll();
        return distractors.stream()
                .map(ChatDistractorDTO::toDto)
                .collect(Collectors.toList());
    }

    // 일부 QnA가져오기 및 세팅

 public List<ChatDistractorDTO> getChatDistractorbyId(Integer distractorId) {

    return chatDistractorRepository.findById(distractorId)
            .map(distractor -> List.of(ChatDistractorDTO.toDto(distractor))) // 하나만 List로 감싸기
            .orElse(List.of()); // 없으면 빈 리스트 반환
}

      // 선택지 저장 
    public void saveDistractor(ChatDistractorDTO dto) {
        ChatDistractor distractor = ChatDistractor.builder()
                .distractorContent(dto.getDistractorContent())
                .distractorDisplayOrder(dto.getDistractorDisplayOrder())
                .distractorLink(dto.getDistractorLink())
                .chatQuestion(chatQuestionRepository.findByQuestionId(dto.getQuestionId()).get(0))
                .build();

        chatDistractorRepository.save(distractor);
    }

    // 선택지 수정
    public void updateDistractor(ChatDistractorDTO dto) {
        ChatDistractor chatDistractor=ChatDistractor.toEntity(dto, null);
        chatDistractor.setChatQuestion(chatQuestionRepository.findByQuestionId(dto.getQuestionId()).get(0));
        chatDistractorRepository.save(chatDistractor);
    }

    // 선택지 삭제 
    public void deleteDistractor(Integer distractorId) {
        if (!chatDistractorRepository.existsById(distractorId)) {
            throw new IllegalArgumentException("삭제할 선택지가 없습니다: " + distractorId);
        }
        chatDistractorRepository.deleteById(distractorId);
    }



    
}
