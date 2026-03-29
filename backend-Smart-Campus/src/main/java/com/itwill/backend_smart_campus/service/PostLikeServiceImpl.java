package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.PostLikeDTO;
import com.itwill.backend_smart_campus.entity.Post;
import com.itwill.backend_smart_campus.entity.PostLike;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.repository.PostLikeRepository;
import com.itwill.backend_smart_campus.repository.PostRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public PostLikeDTO createPostLike(PostLikeDTO postLikeDTO) {
        Post post = postRepository.findById(postLikeDTO.getPostNo())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        UserInfo userInfo = userInfoRepository.findById(postLikeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 중복 추천 방지
        if (postLikeRepository.existsByPostAndUserInfo(post, userInfo)) {
            throw new RuntimeException("이미 추천하셨습니다.");
        }

        PostLike postLike = PostLike.toEntity(postLikeDTO, post, userInfo);
        PostLike saved = postLikeRepository.save(postLike);
        return PostLikeDTO.toDto(saved);
    }

    @Override
    public List<PostLikeDTO> findAllPostLikes() {
        return postLikeRepository.findAll().stream()
                .map(PostLikeDTO::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostLikeDTO findPostLikeById(Long postLikeNo) {
        return postLikeRepository.findById(postLikeNo)
                .map(PostLikeDTO::toDto)
                .orElse(null);
    }

    // @Override
    // public void deletePostLike(Long postLikeNo) {
    // postLikeRepository.deleteById(postLikeNo);
    // }

    @Override
    public boolean existsByPostAndUserInfo(Post post, UserInfo user) {
        return postLikeRepository.existsByPostAndUserInfo(post, user);
    }

    // 🔹 게시글 추천 수 조회
    @Override
    public int countPostLikesByPostNo(Long postNo) {
        return postLikeRepository.countByPost_PostNo(postNo);
    }
}