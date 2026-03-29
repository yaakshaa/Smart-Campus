package com.itwill.backend_smart_campus.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "termgrade")
@SequenceGenerator(name = "TERM_GRADE_SEQ", sequenceName = "term_grade_id_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TERM_GRADE_SEQ")
    @Column(name = "term_grade_id")
    private Long termGradeId; // 학기별 성적 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student; // 학생 ID (FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term; // 학기 ID (FK)

    @Column(name = "attempted_credits")
    private Integer attemptedCredits; // 신청 학점

    @Column(name = "earned_credits")
    private Integer earnedCredits; // 취득 학점

    @Column(name = "grade_score_total", precision = 5, scale = 2)
    private BigDecimal gradeScoreTotal; // 총점 합계

    @Column(name = "grade_score_avg", precision = 5, scale = 2)
    private BigDecimal gradeScoreAvg; // 총점 평균

    @Column(name = "grade_point_sum", precision = 4, scale = 2)
    private BigDecimal gradePointSum; // 평점 합계

    @Column(name = "grade_point_avg", precision = 3, scale = 2)
    private BigDecimal gradePointAvg; // 평점 평균

}
