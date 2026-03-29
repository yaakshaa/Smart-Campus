package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.ClassroomDTO;
import com.itwill.backend_smart_campus.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
@CrossOrigin
public class ClassroomController {

    private final ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<ClassroomDTO> create(@RequestBody ClassroomDTO dto) {
        return ResponseEntity.ok(classroomService.createClassroom(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClassroomDTO>> getAll() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDTO> update(@PathVariable Long id, @RequestBody ClassroomDTO dto) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }
}
