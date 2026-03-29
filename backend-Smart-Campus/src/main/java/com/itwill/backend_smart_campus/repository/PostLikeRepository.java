package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Post;
import com.itwill.backend_smart_campus.entity.PostLike;
import com.itwill.backend_smart_campus.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostAndUserInfo(Post post, UserInfo userInfo);

    int countByPost_PostNo(Long postNo);

    void deleteByPost_PostNo(Long postNo);
}
