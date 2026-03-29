package com.itwill.backend_smart_campus.dto;

import java.util.Date;

import com.itwill.backend_smart_campus.entity.ChatInquiryAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ChatInquiryAnswerDTO {
     private int inquiryAnswerId;
     private int inquiryId;
     private String adminId;
     private String answerContent;
     private Date repliedDate;
     
     public static ChatInquiryAnswerDTO toDto(ChatInquiryAnswer chatInquiryAnswer) {
	return ChatInquiryAnswerDTO.builder()
			.inquiryAnswerId(chatInquiryAnswer.getInquiryAnswerId())
			.inquiryId(chatInquiryAnswer.getInquiryId())
			.adminId(chatInquiryAnswer.getAdminId())
			.answerContent(chatInquiryAnswer.getAnswerContent())
			.repliedDate(chatInquiryAnswer.getRepliedDate())
			.build();
	} 

     
}
