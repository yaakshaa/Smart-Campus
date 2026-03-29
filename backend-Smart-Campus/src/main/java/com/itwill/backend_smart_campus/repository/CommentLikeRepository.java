package com.itwill.backend_smart_campus.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.backend_smart_campus.entity.Comment;
import com.itwill.backend_smart_campus.entity.CommentLike;
import com.itwill.backend_smart_campus.entity.UserInfo;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentAndUserInfo(Comment comment, UserInfo userInfo);

    int countByComment_CommentNo(Long commentNo);

    void deleteByCommentIn(List<Comment> commentList);


}
