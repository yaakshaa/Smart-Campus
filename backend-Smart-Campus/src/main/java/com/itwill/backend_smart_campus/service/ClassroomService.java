package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.ClassroomDTO;

import java.util.List;

public interface ClassroomService {
    ClassroomDTO createClassroom(ClassroomDTO classroomDTO);
    ClassroomDTO getClassroomById(Long id);
    List<ClassroomDTO> getAllClassrooms();
    ClassroomDTO updateClassroom(Long id, ClassroomDTO classroomDTO);
    void deleteClassroom(Long id);
}
