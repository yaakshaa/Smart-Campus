package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.dto.ChatDistractorDTO;
import com.itwill.backend_smart_campus.entity.ChatDistractor;
import com.itwill.backend_smart_campus.entity.ChatQuestion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatDistractorRepository extends JpaRepository<ChatDistractor, Integer> {
    
    //Optional<ChatDistractor> findByLikeChatQuestion_QuestionContent(String questionContent);

    List<ChatDistractor> findByChatQuestionOrderByDistractorDisplayOrder(ChatQuestion chatQuestion);
    List<ChatDistractor> findByChatQuestion(ChatQuestion chatQuestion);
    List<ChatDistractor> findByChatQuestion_QuestionId(ChatQuestion chatquestion);

    // 특정 questionId에 해당하는 distractor 리스트 반환
    List<ChatDistractor> findByChatQuestion_QuestionId(Integer questionId);
    Optional<ChatDistractor> findById(Integer id);
}


