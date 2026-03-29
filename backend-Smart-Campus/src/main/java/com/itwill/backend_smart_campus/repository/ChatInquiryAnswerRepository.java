package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.ChatInquiryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatInquiryAnswerRepository extends JpaRepository<ChatInquiryAnswer, Integer> {

}
