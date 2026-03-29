package com.itwill.backend_smart_campus.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentDTO {
    
    private Long enrollmentId;
    private Long studentId;
    private Long lectureId;
    private LocalDate enrollmentDate;
    private LectureDTO lecture;

}
