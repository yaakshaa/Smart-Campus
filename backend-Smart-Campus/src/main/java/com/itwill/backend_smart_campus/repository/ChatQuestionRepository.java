package com.itwill.backend_smart_campus.repository;
import com.itwill.backend_smart_campus.dto.ChatDistractorDTO;
import com.itwill.backend_smart_campus.dto.ChatQuestionDTO;
import com.itwill.backend_smart_campus.entity.ChatDistractor;
import com.itwill.backend_smart_campus.entity.ChatQuestion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatQuestionRepository extends JpaRepository<ChatQuestion, Integer> { 
    List<ChatQuestion> countByQuestionContentContaining (String questionContent);
    List<ChatQuestion> findByQuestionContentLike(String questionContent);
    List<ChatQuestion> findByQuestionId(Integer questionId);
    
    

    

}
