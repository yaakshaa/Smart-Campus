package com.itwill.backend_smart_campus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.backend_smart_campus.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory_CategoryName(String categoryName);
}
