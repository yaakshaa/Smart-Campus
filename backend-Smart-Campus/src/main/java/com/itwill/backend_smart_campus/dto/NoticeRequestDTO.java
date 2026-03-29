package com.itwill.backend_smart_campus.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeRequestDTO {

    // 수정 시 식별용 
    // 등록 시에는 null 가능
    private Long noticeNo;

    // 공지 제목
    private String noticeTitle;

    // 공지 내용
    private String noticeContent;

    // 작성자 (관리자 ID)
    private String adminId;

    // 삭제할 파일 번호 목록 (수정 시)
    private Long[] deleteFileIds;
}
