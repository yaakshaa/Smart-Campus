package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StudentDTO {

    private Long studentId;
    private String userId;
    private String studentMajor;
    private Integer studentGrade;
    private String studentPhone;
    private String studentStatus;


    public static StudentDTO toDto(Student studentEntity) {
        return StudentDTO.builder()
                .studentId(studentEntity.getStudentId())
                .userId(studentEntity.getUserInfo().getUserId())
                .studentMajor(studentEntity.getStudentMajor())
                .studentGrade(studentEntity.getStudentGrade())
                .studentPhone(studentEntity.getStudentPhone())
                .studentStatus(studentEntity.getStudentStatus())
                .build();
        
    }
}
