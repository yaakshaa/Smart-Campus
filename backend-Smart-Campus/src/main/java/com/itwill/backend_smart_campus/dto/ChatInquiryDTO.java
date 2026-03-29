package com.itwill.backend_smart_campus.dto;

import java.util.Date;

import com.itwill.backend_smart_campus.entity.ChatInquiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatInquiryDTO {
    private int inquiryId; //문의 ID
	private String inquiryTitle; //문의 제목
    private String inquiryEmail; //문의 이메일
    private String inquiryMessage; //문의 내용
    private Date inquiryCreatedAt; //문의 생성 날짜
    private String inquiryAnswered; //대답 여부
	private String inquiryAnswerContent; //대답 내용
	private Date repliedDate; //대답 날짜
	private String adminId; //어드민ID


 	public static ChatInquiryDTO toDto(ChatInquiry ChatInquiry) {
		return ChatInquiryDTO.builder()
				.inquiryId(ChatInquiry.getInquiryId())
				.inquiryTitle(ChatInquiry.getInquiryTitle())
				.inquiryEmail(ChatInquiry.getInquiryEmail())
				.inquiryMessage(ChatInquiry.getInquiryMessage())
				.inquiryCreatedAt(ChatInquiry.getInquiryCreatedAt())
				.inquiryAnswered(ChatInquiry.getInquiryAnswered())
				.inquiryAnswerContent(ChatInquiry.getInquiryAnswerContent())
				.repliedDate(ChatInquiry.getRepliedDate())
				.adminId(ChatInquiry.getAdminId())
				.build();
	}   

}
