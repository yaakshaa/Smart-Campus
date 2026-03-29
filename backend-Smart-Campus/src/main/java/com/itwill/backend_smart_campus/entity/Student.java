package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Student")

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 설정
    @Column(name = "student_id")
    private Long studentId; //학생ID(PK)

    @OneToOne
    @JoinColumn(name = "userinfo_id")
    private UserInfo userInfo; //유저ID_학번(FK)

    @Column(name = "student_major", length = 100)
    private String studentMajor; //학생 전공

    @Column(name = "student_grade")
    private Integer studentGrade; //학생 학년

    @Column(name = "student_phone", unique = true)
    private String studentPhone; //학생 핸드폰번호

    @Column(name = "student_status")
    private String studentStatus; //학생 학적상태 

}
