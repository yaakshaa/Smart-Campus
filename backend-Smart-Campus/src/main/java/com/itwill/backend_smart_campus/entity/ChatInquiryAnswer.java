package com.itwill.backend_smart_campus.entity;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;


import com.itwill.backend_smart_campus.dto.ChatInquiryAnswerDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "chat_Inquiry_Answer")
public class ChatInquiryAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inquiry_answer_id")
     private Integer inquiryAnswerId;

	 @Column(name = "inquiry_id")
     private Integer inquiryId;

	 @Column(name = "admin_id")
     private String adminId;

	 @Column(name = "answer_content")
     private String answerContent;

    @CreationTimestamp
	@Column(name = "replied_date", updatable = false)
	@ColumnDefault(value = "sysdate")
     private Date repliedDate;

   public static ChatInquiryAnswer toEntity(ChatInquiryAnswerDTO chatInquiryAnswerDto) {
	return ChatInquiryAnswer.builder()
			.inquiryAnswerId(chatInquiryAnswerDto.getInquiryAnswerId())
			.inquiryId(chatInquiryAnswerDto.getInquiryId())
			.adminId(chatInquiryAnswerDto.getAdminId())
			.answerContent(chatInquiryAnswerDto.getAnswerContent())
			.repliedDate(chatInquiryAnswerDto.getRepliedDate())
			.build();
	} 


}
