package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
