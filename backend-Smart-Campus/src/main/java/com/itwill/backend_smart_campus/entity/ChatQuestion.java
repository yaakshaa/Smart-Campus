package com.itwill.backend_smart_campus.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import com.itwill.backend_smart_campus.dto.ChatQuestionDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name="chat_Question")
public class ChatQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "question_content")
     private String questionContent;

     
    @CreationTimestamp
	@Column(name = "question_created_at", updatable = false)
	@ColumnDefault(value = "sysdate")
     private Date questionCreatedAt;

public static ChatQuestion toEntity(ChatQuestionDTO chatQuestionDto) {
               return ChatQuestion.builder()
                         .questionId(chatQuestionDto.getQuestionId())
                         .questionContent(chatQuestionDto.getQuestionContent())
                         .questionCreatedAt(chatQuestionDto.getQuestionCreatedAt())
                         .build();     
     }     

     //1:N(질문-대답)
    @OneToMany(mappedBy = "chatQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
     @Builder.Default
    private List<ChatDistractor> chatDistractor = new ArrayList<>();
    
}
