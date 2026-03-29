package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.CommentDTO;
import java.util.List;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO);

    List<CommentDTO> findAllComments();

    CommentDTO findCommentById(Long commentNo);

    CommentDTO updateComment(Long commentNo, CommentDTO commentDTO);

    void deleteComment(Long commentNo);

    List<CommentDTO> getCommentsByPostNo(Long postNo);
}
