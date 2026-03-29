package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.CommentLike;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentLikeDTO {

    private Long commentLikeNo;
    private Long commentNo;
    private String userId;

    public static CommentLikeDTO toDto(CommentLike commentLike) {
        return CommentLikeDTO.builder()
                .commentLikeNo(commentLike.getCommentLikeNo())
                .commentNo(commentLike.getComment().getCommentNo())
                .userId(commentLike.getUserInfo().getUserId())
                .build();
    }
}
