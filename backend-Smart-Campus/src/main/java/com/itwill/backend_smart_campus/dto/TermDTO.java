package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Term;

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
public class TermDTO {
    private Long termId;
    private Long academicYear;
    private String semester;

    public static TermDTO fromEntity(Term term) {
        return TermDTO.builder()
                .termId(term.getId())
                .academicYear(term.getAcademicYear())
                .semester(term.getSemester())
                .build();
    }
}
