package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Professor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorDTO {

    private Long professorId;
    private String userId;  // FK: UserInfo와 연결된 ID 값
    private String professorDepartment;

    public static ProfessorDTO toDto(Professor professor) {
        return ProfessorDTO.builder()
                .professorId(professor.getProfessorId())
                .userId(professor.getUserInfo().getUserId())
                .professorDepartment(professor.getProfessorDepartment())
                .build();
    }
}