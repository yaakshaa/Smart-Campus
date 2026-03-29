package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.ChatDistractor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDistractorDTO {

    private int distractorId;
    private int distractorDisplayOrder;
	private int questionId;
    private String distractorContent;
    private String distractorLink;  
    private String questionContent;
    
  

public static ChatDistractorDTO toDto(ChatDistractor chatDistractor) {
    return ChatDistractorDTO.builder()
        .distractorId(chatDistractor.getDistractorId())
        .questionId(chatDistractor.getChatQuestion() != null 
            ? chatDistractor.getChatQuestion().getQuestionId() 
            : 0) 
        .distractorDisplayOrder(chatDistractor.getDistractorDisplayOrder())
        .distractorContent(chatDistractor.getDistractorContent())
        .distractorLink(chatDistractor.getDistractorLink())
        .questionContent(chatDistractor.getChatQuestion() != null 
                ? chatDistractor.getChatQuestion().getQuestionContent() 
                : null) 
                
        .build(); 
}   
}
