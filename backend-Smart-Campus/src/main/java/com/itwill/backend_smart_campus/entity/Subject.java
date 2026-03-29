package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_no")
    private Long subjectNo; 

    @Column(name = "subject_name", length = 100)
    private String subjectName; 

    @Column(name = "subject_type")
    private String subjectType; 

    @Column(name = "credit")
    private Integer credit; 
} 