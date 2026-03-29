package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.CommentLikeDTO;
import com.itwill.backend_smart_campus.entity.Comment;
import com.itwill.backend_smart_campus.entity.CommentLike;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.repository.CommentLikeRepository;
import com.itwill.backend_smart_campus.repository.CommentRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public CommentLikeDTO createCommentLike(CommentLikeDTO dto) {
        Comment comment = commentRepository.findById(dto.getCommentNo())
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        UserInfo userInfo = userInfoRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // ✅ 중복 좋아요 방지
        boolean alreadyLiked = commentLikeRepository.existsByCommentAndUserInfo(comment, userInfo);
        if (alreadyLiked) {
            throw new RuntimeException("이미 좋아요를 누르셨습니다.");
        }

        CommentLike commentLike = CommentLike.toEntity(dto, comment, userInfo);
        CommentLike saved = commentLikeRepository.save(commentLike);
        return CommentLikeDTO.toDto(saved);
    }

    @Override
    public List<CommentLikeDTO> findAllCommentLikes() {
        return commentLikeRepository.findAll().stream()
                .map(CommentLikeDTO::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentLikeDTO findCommentLikeById(Long commentLikeNo) {
        return commentLikeRepository.findById(commentLikeNo)
                .map(CommentLikeDTO::toDto)
                .orElse(null);
    }

    @Override
    public int countCommentLikesByCommentNo(Long commentNo) {
        return commentLikeRepository.countByComment_CommentNo(commentNo);
    }
}
