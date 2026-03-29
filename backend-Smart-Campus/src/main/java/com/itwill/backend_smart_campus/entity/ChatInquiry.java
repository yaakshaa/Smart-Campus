package com.itwill.backend_smart_campus.entity;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.itwill.backend_smart_campus.dto.ChatInquiryDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor

@Entity
@Table(name = "chat_Inquiry")
@DynamicUpdate
public class ChatInquiry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inquiry_id")
    private Integer inquiryId;

	@Column(name = "inquiry_email")
    private String inquiryEmail;
	
	@Column(name = "inquiry_message")
    private String inquiryMessage;
	
	@Builder.Default
	@Column(name = "inquiry_answered", length = 1)
    private String inquiryAnswered = "N";

    @CreationTimestamp
	@Column(name = "inquiry_created_at", updatable = false)
	@ColumnDefault(value = "sysdate")
    private Date inquiryCreatedAt;

	@Column(name = "inquiry_title")
    private String inquiryTitle;

	@Column(name = "inquiry_Answer_Content")
    private String inquiryAnswerContent;
	

	@Column(name = "replied_date")
	private Date repliedDate; //대답 날짜

	@Column(name = "admin_id")
	private String adminId; //어드민ID


 	public static ChatInquiry toEntity(ChatInquiryDTO chatInquiryDto) {
		return ChatInquiry.builder()
				.inquiryId(chatInquiryDto.getInquiryId())
				.inquiryTitle(chatInquiryDto.getInquiryTitle())
				.inquiryEmail(chatInquiryDto.getInquiryEmail())
				.inquiryMessage(chatInquiryDto.getInquiryMessage())
				.inquiryCreatedAt(chatInquiryDto.getInquiryCreatedAt())
				.inquiryAnswered(chatInquiryDto.getInquiryAnswered())
				.inquiryAnswerContent(chatInquiryDto.getInquiryAnswerContent())
				.repliedDate(chatInquiryDto.getRepliedDate())
				.adminId(chatInquiryDto.getAdminId())
				.build();
	}   
    

    
}
