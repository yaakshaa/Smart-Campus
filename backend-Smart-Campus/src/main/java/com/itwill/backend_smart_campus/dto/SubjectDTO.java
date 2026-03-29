package com.itwill.backend_smart_campus.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SubjectDTO {
   
    private Long subjectNo;
    private String subjectName;
    private String subjectType;
    private Integer credit;

}
