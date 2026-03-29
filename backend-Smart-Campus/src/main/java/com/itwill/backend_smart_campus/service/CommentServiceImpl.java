package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.CommentDTO;
import com.itwill.backend_smart_campus.entity.Comment;
import com.itwill.backend_smart_campus.entity.Post;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.repository.CommentLikeRepository;
import com.itwill.backend_smart_campus.repository.CommentRepository;
import com.itwill.backend_smart_campus.repository.PostRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserInfoRepository userInfoRepository;
    private final CommentLikeRepository commentLikeRepository; 

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        Post post = postRepository.findById(commentDTO.getPostNo())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        UserInfo userInfo = userInfoRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Comment comment = Comment.toEntity(commentDTO, post, userInfo);
        Comment saved = commentRepository.save(comment);
        return CommentDTO.toDto(saved);
    }

    @Override
    public List<CommentDTO> findAllComments() {
        return commentRepository.findAll().stream()
                .map(CommentDTO::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO findCommentById(Long commentNo) {
        return commentRepository.findById(commentNo)
                .map(CommentDTO::toDto)
                .orElse(null);
    }

    @Override
    public CommentDTO updateComment(Long commentNo, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        comment.setCommentContent(commentDTO.getCommentContent());
        Comment updated = commentRepository.save(comment);
        return CommentDTO.toDto(updated);
    }

    @Override
    public void deleteComment(Long commentNo) {
        commentRepository.deleteById(commentNo);
    }

    @Override
    public List<CommentDTO> getCommentsByPostNo(Long postNo) {
        List<Comment> commentList = commentRepository.findByPost_PostNo(postNo);
        return commentList.stream().map(comment -> {
            CommentDTO dto = CommentDTO.toDto(comment);
            
            
            int likeCount = commentLikeRepository.countByComment_CommentNo(comment.getCommentNo());
            dto.setCommentLikeCount(likeCount);

            return dto;
        }).collect(Collectors.toList());
    }
}
