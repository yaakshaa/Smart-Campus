package com.itwill.backend_smart_campus.entity;

import com.itwill.backend_smart_campus.dto.CommentLikeDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment_like")
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_no")
    private Long commentLikeNo; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_no", nullable = false)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userinfo_id", nullable = false)
    private UserInfo userInfo;

    public static CommentLike toEntity(CommentLikeDTO dto, Comment comment, UserInfo userInfo) {
        return CommentLike.builder()
                .commentLikeNo(dto.getCommentLikeNo()) 
                .comment(comment)                      
                .userInfo(userInfo)                   
                .build();
    }
}
