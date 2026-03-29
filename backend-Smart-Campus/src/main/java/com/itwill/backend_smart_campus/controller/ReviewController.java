package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.ReviewDTO;
import com.itwill.backend_smart_campus.entity.Review;
import com.itwill.backend_smart_campus.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // ✅ 순환참조 방지를 위해 Review → ReviewDTO로 변환하여 반환
    @GetMapping
    public List<ReviewDTO> getAll() {
        List<Review> reviews = reviewService.findAll();

        return reviews.stream().map(review -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setReviewId(review.getId()); // ✅ 올바른 필드명
            dto.setRating(review.getRating());
            dto.setReviewcomment(review.getReviewcomment());
            dto.setCreatedAt(review.getCreatedAt());

            if (review.getUserInfo() != null) {
                dto.setUserId(review.getUserInfo().getUserId());
            }
            if (review.getMenu() != null) {
                dto.setMenuId(review.getMenu().getId());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @PostMapping
    public Review create(@RequestBody ReviewDTO dto) {
        return reviewService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.deleteById(id);
    }
}
