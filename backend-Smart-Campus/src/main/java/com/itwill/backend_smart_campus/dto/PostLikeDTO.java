package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.PostLike;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLikeDTO {

    private Long postLikeNo;
    private Long postNo;
    private String userId;

    public static PostLikeDTO toDto(PostLike postLike) {
        return PostLikeDTO.builder()
                .postLikeNo(postLike.getPostLikeNo())
                .postNo(postLike.getPost().getPostNo())
                .userId(postLike.getUserInfo().getUserId())
                .build();
    }
}
