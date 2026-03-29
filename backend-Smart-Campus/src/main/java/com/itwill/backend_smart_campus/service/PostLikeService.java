package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.PostLikeDTO;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.entity.Post;

import java.util.List;

public interface PostLikeService {

    
    PostLikeDTO createPostLike(PostLikeDTO postLikeDTO);

    List<PostLikeDTO> findAllPostLikes();

    PostLikeDTO findPostLikeById(Long postLikeNo);

    //void deletePostLike(Long postLikeNo);

    public boolean existsByPostAndUserInfo(Post post, UserInfo user);

    int countPostLikesByPostNo(Long postNo);
}
