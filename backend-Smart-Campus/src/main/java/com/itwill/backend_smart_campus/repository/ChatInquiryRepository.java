package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.dto.ChatMainMenuDTO;
import com.itwill.backend_smart_campus.entity.ChatInquiry;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatInquiryRepository extends JpaRepository<ChatInquiry, Integer> {
    List<ChatInquiry> findByInquiryId(Integer inquiryId);
    List<ChatInquiry> findAllByOrderByInquiryIdDesc();
}