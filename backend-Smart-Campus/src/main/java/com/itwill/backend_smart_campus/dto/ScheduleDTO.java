package com.itwill.backend_smart_campus.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ScheduleDTO {

    private Long scheduleId;
    private Long lectureId;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private Long classroomId;

}