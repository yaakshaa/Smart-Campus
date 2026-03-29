package com.itwill.backend_smart_campus.entity;

import com.itwill.backend_smart_campus.dto.PostDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    private Long postNo;

    @Column(name = "post_title", nullable = false, length = 200)
    private String postTitle;

    @Column(name = "post_content", nullable = false, columnDefinition = "CLOB")
    private String postContent;

    @Column(name = "post_created", nullable = false)
    private LocalDateTime postCreated;

    @Column(name = "post_view", nullable = false)
    private int postView;

    @Column(name = "post_like_count", nullable = false)
    private int postLikeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userinfo_id", nullable = false)
    private UserInfo userInfo;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public static Post toEntity(PostDTO dto, UserInfo userInfo, Category category) {
        return Post.builder()
                .postTitle(dto.getPostTitle())
                .postContent(dto.getPostContent())
                .postCreated(dto.getPostCreated() != null ? dto.getPostCreated() : LocalDateTime.now())
                .postView(dto.getPostView() != 0 ? dto.getPostView() : 0)
                .postLikeCount(dto.getPostLikeCount() != 0 ? dto.getPostLikeCount() : 0)
                .userInfo(userInfo)
                .category(category)
                .build();
    }
}
