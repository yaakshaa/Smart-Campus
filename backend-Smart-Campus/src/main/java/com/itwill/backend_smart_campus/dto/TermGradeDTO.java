package com.itwill.backend_smart_campus.dto;

import java.math.BigDecimal;

import com.itwill.backend_smart_campus.entity.TermGrade;

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
public class TermGradeDTO {
    private Long termGradeId;
    private Long studentId;
    private Long termId;
    private Integer attemptedCredits;
    private Integer earnedCredits;
    private BigDecimal gradeScoreTotal;
    private BigDecimal gradeScoreAvg;
    private BigDecimal gradePointSum;
    private BigDecimal gradePointAvg;

    public static TermGradeDTO fromEntity(TermGrade termGrade){
        return TermGradeDTO.builder()
                .termGradeId(termGrade.getTermGradeId())
                .studentId(termGrade.getStudent().getStudentId())
                .termId(termGrade.getTerm().getId())
                .attemptedCredits(termGrade.getAttemptedCredits())
                .earnedCredits(termGrade.getEarnedCredits())
                .gradeScoreTotal(termGrade.getGradeScoreTotal())
                .gradeScoreAvg(termGrade.getGradeScoreAvg())
                .gradePointSum(termGrade.getGradePointSum())
                .gradePointAvg(termGrade.getGradePointAvg())
                .build();
    }
}

