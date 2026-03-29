package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.ReviewDTO;
import com.itwill.backend_smart_campus.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> findAll();
    Optional<Review> findById(Long id);
    Review save(ReviewDTO dto);
    void deleteById(Long id);
}
