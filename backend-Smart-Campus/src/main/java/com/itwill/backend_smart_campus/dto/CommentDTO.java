package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Comment;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long commentNo;         
    private Long postNo;            
    private String userId;          
    private String commentContent;   
    private LocalDateTime commentCreated;
    private Integer commentLikeCount;

    public static CommentDTO toDto(Comment comment) {
        return CommentDTO.builder()
                .commentNo(comment.getCommentNo())
                .postNo(comment.getPost().getPostNo())
                .userId(comment.getUserInfo().getUserId())
                .commentContent(comment.getCommentContent())
                .commentCreated(comment.getCommentDate())
                .build();
    }
}
