package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.PostDTO;
import com.itwill.backend_smart_campus.entity.Category;
import com.itwill.backend_smart_campus.entity.Comment;
import com.itwill.backend_smart_campus.entity.Post;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.repository.CommentLikeRepository;
import com.itwill.backend_smart_campus.repository.CommentRepository;
import com.itwill.backend_smart_campus.repository.PostLikeRepository;
import com.itwill.backend_smart_campus.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO, UserInfo userInfo, Category category) {
        Post post = Post.toEntity(postDTO, userInfo, category);
        Post saved = postRepository.save(post);
        return PostDTO.toDto(saved);
    }

    @Override
    public List<PostDTO> findAllPosts() {
        return postRepository.findAll().stream()
                .sorted(Comparator.comparing(Post::getPostCreated).reversed())
                .map(PostDTO::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO findPostById(Long postNo) {
        return postRepository.findById(postNo)
                .map(PostDTO::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public PostDTO findPostAndIncrementView(Long postNo) {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        post.setPostView(post.getPostView() + 1);
        Post updated = postRepository.save(post);

        return PostDTO.toDto(updated);
    }

    @Override
    public PostDTO updatePost(Long postNo, PostDTO postDTO) {
        Post post = postRepository.findById(postNo).orElseThrow(() -> new RuntimeException("게시글 없음"));

        post.setPostTitle(postDTO.getPostTitle());
        post.setPostContent(postDTO.getPostContent());

        Post updated = postRepository.save(post);
        return PostDTO.toDto(updated);
    }

    @Override
    @Transactional
    public void deletePost(Long postNo) {
        List<Comment> commentList = commentRepository.findByPost_PostNo(postNo);

        if (!commentList.isEmpty()) {
            commentLikeRepository.deleteByCommentIn(commentList);
        }

        commentRepository.deleteByPost_PostNo(postNo);
        postLikeRepository.deleteByPost_PostNo(postNo);
        postRepository.deleteById(postNo);
    }

    @Override
    public Post findPostEntity(Long postNo) {
        return postRepository.findById(postNo)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
    }

    @Override
    public void incrementPostLikeCount(Post post) {
        post.setPostLikeCount(post.getPostLikeCount() + 1);
        postRepository.save(post);
    }

    @Override
    public List<PostDTO> findPostsByCategoryName(String categoryName) {
        return postRepository.findByCategory_CategoryName(categoryName)
                .stream()
                .map(PostDTO::toDto)
                .collect(Collectors.toList());
    }
}
