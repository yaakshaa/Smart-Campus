package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "enrollment_id")
    private Long enrollmentId; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", referencedColumnName = "lecture_id", nullable = false)
    private Lecture lecture; 

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate; 
}