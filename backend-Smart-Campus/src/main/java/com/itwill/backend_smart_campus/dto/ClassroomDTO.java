package com.itwill.backend_smart_campus.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClassroomDTO {
   
    private Long classroomId;
    private String building;
    private Integer roomNumber;

}
