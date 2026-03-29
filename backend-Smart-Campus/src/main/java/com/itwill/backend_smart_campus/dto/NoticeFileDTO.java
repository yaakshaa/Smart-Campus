package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.NoticeFile;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeFileDTO {

    private Long fileNo;
    private String fileName;
    private String filePath;

    public static NoticeFileDTO toDto(NoticeFile noticeFile) {
        return NoticeFileDTO.builder()
            .fileNo(noticeFile.getFileNo())
            .fileName(noticeFile.getFileName())
            .filePath(noticeFile.getFilePath())
            .build();
    }
}
