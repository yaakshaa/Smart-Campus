package com.itwill.backend_smart_campus.dto;
import java.util.Date;
import java.util.List;

import com.itwill.backend_smart_campus.entity.ChatDistractor;
import com.itwill.backend_smart_campus.entity.ChatQuestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

     public class ChatQuestionDTO {
          private int questionId;
          private String questionContent;
          private Date questionCreatedAt;
          private List<ChatDistractorDTO> listChatDistractorDTO;

     public static ChatQuestionDTO toDto(ChatQuestion chatQuestion) {
               return ChatQuestionDTO.builder()
                         .questionId(chatQuestion.getQuestionId())
                         .questionContent(chatQuestion.getQuestionContent())
                         .questionCreatedAt(chatQuestion.getQuestionCreatedAt())
                         .build();     
     }
     
    
}
