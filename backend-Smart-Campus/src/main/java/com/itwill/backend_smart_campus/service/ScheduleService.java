package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleService {
    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);
    ScheduleDTO getScheduleById(Long id);
    List<ScheduleDTO> getAllSchedules();
    ScheduleDTO updateSchedule(Long id, ScheduleDTO scheduleDTO);
    void deleteSchedule(Long id);
}
