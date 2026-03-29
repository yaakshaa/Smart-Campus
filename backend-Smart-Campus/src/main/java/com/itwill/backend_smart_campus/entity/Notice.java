package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_seq_generator")
    // @SequenceGenerator(name = "notice_seq_generator", sequenceName = "notice_seq", allocationSize = 1)
    @Column(name = "notice_no")
    private Long noticeNo;  // 공지사항 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;     // 관리자 ID (Admin)

    @Column(name = "notice_title", nullable = false, length = 200)
    private String noticeTitle;     // 공지사항 제목

    @Lob
    @Column(name = "notice_content", nullable = false)
    private String noticeContent;   // 공지사항 내용

    @Column(name = "notice_count", nullable = false)
    private int noticeCount;        // 공지사항 조회수

    @Column(name = "notice_created_at", nullable = false)
    private LocalDateTime noticeCreatedAt;      // 공지사항 작성일자
}
