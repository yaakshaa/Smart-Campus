package com.itwill.backend_smart_campus.service;

import java.util.List;
import com.itwill.backend_smart_campus.entity.Post;
import com.itwill.backend_smart_campus.dto.PostDTO;
import com.itwill.backend_smart_campus.entity.Category;
import com.itwill.backend_smart_campus.entity.UserInfo;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, UserInfo userInfo, Category category); 

    List<PostDTO> findAllPosts();

    PostDTO findPostById(Long postNo);

    PostDTO updatePost(Long postNo, PostDTO postDTO);

    void deletePost(Long postNo);

    public Post findPostEntity(Long postNo);

    public void incrementPostLikeCount(Post post);

    List<PostDTO> findPostsByCategoryName(String categoryName);

    PostDTO findPostAndIncrementView(Long postNo);
}
