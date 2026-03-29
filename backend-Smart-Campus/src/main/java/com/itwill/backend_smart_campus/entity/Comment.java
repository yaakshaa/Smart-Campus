package com.itwill.backend_smart_campus.entity;

import com.itwill.backend_smart_campus.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo; 

    @Column(name = "comment_content", nullable = false, columnDefinition = "CLOB")
    private String commentContent; 

    @Column(name = "comment_created", nullable = false) 
    private LocalDateTime commentDate; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userinfo_id", nullable = false)
    private UserInfo userInfo;

    public static Comment toEntity(CommentDTO dto, Post post, UserInfo userInfo) {
        return Comment.builder()
                .commentNo(dto.getCommentNo())
                .commentContent(dto.getCommentContent())
                .commentDate(dto.getCommentCreated() != null ? dto.getCommentCreated() : LocalDateTime.now())
                .post(post)
                .userInfo(userInfo)
                .build();
    }
}
