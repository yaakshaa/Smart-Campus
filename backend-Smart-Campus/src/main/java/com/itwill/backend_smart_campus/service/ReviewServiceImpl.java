package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.ReviewDTO;
import com.itwill.backend_smart_campus.entity.Menu;
import com.itwill.backend_smart_campus.entity.Review;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.repository.MenuRepository;
import com.itwill.backend_smart_campus.repository.ReviewRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review save(ReviewDTO dto) {
        Menu menu = menuRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다."));
        UserInfo user = userInfoRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Review review = Review.builder()
                .menu(menu)
                .userInfo(user)
                .rating(dto.getRating())
                .reviewcomment(dto.getReviewcomment())
                .createdAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}
