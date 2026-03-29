package com.itwill.backend_smart_campus.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureDTO {

    private Long lectureId;
    private Long professorId;
    private Long subjectNo;
    private String professorName;
    private Long termId;
    private Long classroomId;
    private Integer credit;
    private String subjectName;
    private String classroomName;
    private Integer maxEnrollment; 
}
