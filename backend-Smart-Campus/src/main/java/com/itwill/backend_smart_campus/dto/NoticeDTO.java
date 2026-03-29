package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private Long noticeNo;
    private String adminId;
    private String noticeTitle;
    private String noticeContent;
    private int noticeCount;
    private LocalDateTime noticeCreatedAt;

    private List<NoticeFileDTO> files;  // 첨부파일 리스트 포함

    public static NoticeDTO toDto(Notice notice) {
        return NoticeDTO.builder()
            .noticeNo(notice.getNoticeNo())
            .adminId(notice.getAdmin().getAdminId())
            .noticeTitle(notice.getNoticeTitle())
            .noticeContent(notice.getNoticeContent())
            .noticeCount(notice.getNoticeCount())
            .noticeCreatedAt(notice.getNoticeCreatedAt())
            .build();

            // files는 파일 조회 쿼리가 필요해서 서비스에서 setFiles()로 추가 가능
    }
}
