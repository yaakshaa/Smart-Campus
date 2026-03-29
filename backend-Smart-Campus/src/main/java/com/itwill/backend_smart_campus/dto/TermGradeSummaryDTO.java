package com.itwill.backend_smart_campus.dto;

import java.math.BigDecimal;

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
public class TermGradeSummaryDTO { // 총점, 평점 계산 용도 DTO
    
    private int attemptedCredits;       // 신청 학점
    private int earnedCredits;          // 이수 학점
    private BigDecimal gradeScoreTotal; // 총점 합계
    private BigDecimal gradeScoreAvg;   // 총점 평균
    private BigDecimal gradePointSum;   // 평점 합계
    private BigDecimal gradePointAvg;   // 평점 평균
}
