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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "gradeinfo")
@SequenceGenerator(name = "GRADEINFO_SEQ", sequenceName = "gradeinfo_id_seq", allocationSize = 1)
public class GradeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GRADEINFO_SEQ")
    @Column(name = "gradeinfo_id")
    private Long id; // 성적 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student; // 학생 ID (FK → Student)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture; // 강의 ID (FK → Lecture)

    @Column(name = "grade_score", precision = 5, scale = 2)
    private BigDecimal gradeScore; // 총점

    @Column(name = "grade_point", precision = 3, scale = 2)
    private BigDecimal gradePoint; // 평점

    @Column(name = "grade_letter", length = 2)
    private String gradeLetter; // 등급 (A+, B0 등)
    
    @Column(name = "retake_flag", length = 1)
    private String retakeFlag; // 재수강 여부 ('Y' or 'N')

    @Column(name = "attempted_credits")
    private Integer attemptedCredits;

    @Column(name = "earned_credits")
    private Integer earnedCredits;

    @PrePersist
    @PreUpdate
    public void calculateDerivedFields() {
        // 등급 계산
        if (gradeScore != null) {
            int score = gradeScore.intValue();
            if (score >= 95) gradeLetter = "A+";
            else if (score >= 90) gradeLetter = "A0";
            else if (score >= 85) gradeLetter = "B+";
            else if (score >= 80) gradeLetter = "B0";
            else if (score >= 75) gradeLetter = "C+";
            else if (score >= 70) gradeLetter = "C0";
            else if (score >= 65) gradeLetter = "D+";
            else if (score >= 60) gradeLetter = "D0";
            else gradeLetter = "F";
        }

        // 재수강 필요 여부
        if (gradePoint != null && gradePoint.compareTo(BigDecimal.valueOf(1.0)) < 0) {
            retakeFlag = "Y";
        } else {
            retakeFlag = "N";
        }
    }
}
