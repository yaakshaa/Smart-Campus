package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "term")
@SequenceGenerator(name = "TERM_SEQ", sequenceName = "term_id_seq", allocationSize = 1)
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TERM_SEQ")
    @Column(name = "term_id")
    private Long id; // 학기 ID (PK)

    @Column(name = "year")
    private Long academicYear;  // 연도 (예: 2025)

    @Column(name = "semester")
    private String semester;  // 학기 (예: 1, 2, Summer 등)

}
