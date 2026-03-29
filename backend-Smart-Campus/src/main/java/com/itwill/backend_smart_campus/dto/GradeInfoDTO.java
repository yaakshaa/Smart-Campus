package com.itwill.backend_smart_campus.dto;

import java.math.BigDecimal;

import com.itwill.backend_smart_campus.entity.GradeInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeInfoDTO {
    private Long gradeInfoId;
    private Long studentId;
    private Long lectureId;

    private String subjectName;
    private String subjectType;
    private Integer credit;

    private BigDecimal gradeScore; // 총점
    private BigDecimal gradePoint; // 평점
    private String gradeLetter;    // 등급
    private String retakeFlag;     // 재수강 여부(Y or N)

    public static GradeInfoDTO fromEntity(GradeInfo gradeInfo) {
        return GradeInfoDTO.builder()
                .gradeInfoId(gradeInfo.getId())
                .studentId(gradeInfo.getStudent().getStudentId())
                .lectureId(gradeInfo.getLecture().getLectureId())
                .subjectName(gradeInfo.getLecture().getSubject().getSubjectName())
                .subjectType(gradeInfo.getLecture().getSubject().getSubjectType())
                .credit(gradeInfo.getLecture().getSubject().getCredit())
                .gradeScore(gradeInfo.getGradeScore())
                .gradePoint(gradeInfo.getGradePoint())
                .gradeLetter(gradeInfo.getGradeLetter())
                .retakeFlag(gradeInfo.getRetakeFlag())
                .build();
    }
}
