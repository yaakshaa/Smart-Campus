package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.ClassroomDTO;
import com.itwill.backend_smart_campus.entity.Classroom;
import com.itwill.backend_smart_campus.repository.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    private ClassroomDTO toDTO(Classroom entity) {
        return ClassroomDTO.builder()
                .classroomId(entity.getClassroomId())
                .building(entity.getBuilding())
                .roomNumber(entity.getRoomNumber())
                .build();
    }

    private Classroom toEntity(ClassroomDTO dto) {
        return Classroom.builder()
                .classroomId(dto.getClassroomId())
                .building(dto.getBuilding())
                .roomNumber(dto.getRoomNumber())
                .build();
    }

    @Override
    public ClassroomDTO createClassroom(ClassroomDTO classroomDTO) {
        Classroom classroom = classroomRepository.save(toEntity(classroomDTO));
        return toDTO(classroom);
    }

    @Override
    public ClassroomDTO getClassroomById(Long id) {
        return classroomRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("강의실을 찾을 수 없습니다: id=" + id));
    }

    @Override
    public List<ClassroomDTO> getAllClassrooms() {
        return classroomRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassroomDTO updateClassroom(Long id, ClassroomDTO classroomDTO) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("강의실을 찾을 수 없습니다: id=" + id));
        
        classroom.setBuilding(classroomDTO.getBuilding());
        classroom.setRoomNumber(classroomDTO.getRoomNumber());

        return toDTO(classroomRepository.save(classroom));
    }

    @Override
    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }
}
