package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.CommentLikeDTO;
import java.util.List;

public interface CommentLikeService {

    CommentLikeDTO createCommentLike(CommentLikeDTO commentLikeDTO);

    List<CommentLikeDTO> findAllCommentLikes();

    CommentLikeDTO findCommentLikeById(Long commentLikeNo);

  //  void deleteCommentLike(Long commentLikeNo);

    int countCommentLikesByCommentNo(Long commentNo);
}
