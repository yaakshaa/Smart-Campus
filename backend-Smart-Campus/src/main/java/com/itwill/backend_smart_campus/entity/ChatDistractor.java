package com.itwill.backend_smart_campus.entity;

import com.itwill.backend_smart_campus.dto.ChatDistractorDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "chat_Distractor")
public class ChatDistractor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distractor_id")
    private int distractorId;

    @Column(name = "distractor_display_order")
    private int distractorDisplayOrder;

    @Column(name = "distractor_content")
    private String distractorContent;
    
    @Column(name = "distractor_link")
    private String distractorLink;  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id") 
    private ChatQuestion chatQuestion; // 대답:질문 N:1

    public static ChatDistractor toEntity(ChatDistractorDTO chatDistractorDTO,ChatQuestion chatQuestion) {
        return ChatDistractor.builder()
            .distractorId(chatDistractorDTO.getDistractorId())
            .distractorDisplayOrder(chatDistractorDTO.getDistractorDisplayOrder())
            .distractorContent(chatDistractorDTO.getDistractorContent())
            .distractorLink(chatDistractorDTO.getDistractorLink())
            .chatQuestion(chatQuestion)
            .build();        
    } 
        public String getQuestionContent() {
        return chatQuestion != null ? chatQuestion.getQuestionContent() : null;
    }

}
