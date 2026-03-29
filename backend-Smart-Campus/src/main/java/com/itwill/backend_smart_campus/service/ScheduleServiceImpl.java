package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.ScheduleDTO;
import com.itwill.backend_smart_campus.entity.Classroom;
import com.itwill.backend_smart_campus.entity.Lecture;
import com.itwill.backend_smart_campus.entity.Schedule;
import com.itwill.backend_smart_campus.repository.ClassroomRepository;
import com.itwill.backend_smart_campus.repository.LectureRepository;
import com.itwill.backend_smart_campus.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LectureRepository lectureRepository;
    private final ClassroomRepository classroomRepository;

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO dto) {
        Lecture lecture = lectureRepository.findById(dto.getLectureId())
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        Schedule schedule = Schedule.builder()
                .lecture(lecture)
                .classroom(classroom)
                .dayOfWeek(dto.getDayOfWeek())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        return toDTO(scheduleRepository.save(schedule));
    }

    @Override
    public ScheduleDTO getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO updateSchedule(Long id, ScheduleDTO dto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Lecture lecture = lectureRepository.findById(dto.getLectureId())
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        schedule.setLecture(lecture);
        schedule.setClassroom(classroom);
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());

        return toDTO(scheduleRepository.save(schedule));
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    private ScheduleDTO toDTO(Schedule schedule) {
        return ScheduleDTO.builder()
                .scheduleId(schedule.getScheduleId())
                .lectureId(schedule.getLecture().getLectureId())
                .classroomId(schedule.getClassroom().getClassroomId())
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }
}
