package com.itwill.backend_smart_campus.entity;

import com.itwill.backend_smart_campus.dto.PostLikeDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post_like")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_no")
    private Long postLikeNo; 

   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no", nullable = false)
    private Post post;

   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userinfo_id", nullable = false)
    private UserInfo userInfo;

   
    public static PostLike toEntity(PostLikeDTO dto, Post post, UserInfo userInfo) {
        return PostLike.builder()
                .postLikeNo(dto.getPostLikeNo())
                .post(post)             
                .userInfo(userInfo)    
                .build();
    }
}
