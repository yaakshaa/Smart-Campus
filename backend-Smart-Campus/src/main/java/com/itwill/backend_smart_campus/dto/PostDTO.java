package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private Long postNo;
    private String postTitle;
    private String postContent;
    private String userId;        
    private Long categoryNo;      
    private String categoryName;  
    private LocalDateTime postCreated; 
    @Builder.Default
    private int postView = 0;         

    @Builder.Default
    private int postLikeCount = 0;    

    @Builder.Default
    private int commentCount = 0;     

    public static PostDTO toDto(Post post) {
        return PostDTO.builder()
                .postNo(post.getPostNo())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .userId(post.getUserInfo() != null ? post.getUserInfo().getUserId() : null)
                .categoryNo(post.getCategory() != null ? post.getCategory().getCategoryNo() : null)
                .categoryName(post.getCategory() != null ? post.getCategory().getCategoryName() : null)
                .postCreated(post.getPostCreated())
                .postView(post.getPostView())
                .postLikeCount(post.getPostLikeCount())
                .commentCount(post.getComments() != null ? post.getComments().size() : 0)
                .build();
    }
}
