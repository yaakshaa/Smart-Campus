package com.itwill.backend_smart_campus.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long reviewId;     
    private String userId;      
    private Long menuId;         

    private Integer rating;
    private String reviewcomment;
    private LocalDateTime createdAt;

    public static ReviewDTO toDto(com.itwill.backend_smart_campus.entity.Review review) {
        return ReviewDTO.builder()
                .reviewId(review.getId())
                .userId(review.getUserInfo().getUserId())
                .menuId(review.getMenu().getId())
                .rating(review.getRating())
                .reviewcomment(review.getReviewcomment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
