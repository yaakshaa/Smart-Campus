package com.itwill.backend_smart_campus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.backend_smart_campus.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_PostNo(Long postNo);
    
    void deleteByPost_PostNo(Long postNo);
}
