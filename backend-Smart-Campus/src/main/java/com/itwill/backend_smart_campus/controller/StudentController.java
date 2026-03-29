package com.itwill.backend_smart_campus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.StudentDTO;
import com.itwill.backend_smart_campus.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "학생등록")
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO dto) {
        studentService.createStudent(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "학번으로 학생 정보조회")
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> findStudentById(@PathVariable Long studentId) {
        StudentDTO dto = studentService.findStudentById(studentId);
        return ResponseEntity.ok(dto);
    }
    
    @Operation(summary = "이름으로 학생 정보조회")
    @GetMapping("/name")
    public ResponseEntity<List<StudentDTO>> findStudentByName(@RequestParam String name) {
        List<StudentDTO> dtos = studentService.findStudentByName(name);
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "이름으로 전공 정보조회")
    @GetMapping("/major")
    public ResponseEntity<List<StudentDTO>> findStudentByMajor(@RequestParam String major) {
        List<StudentDTO> dtos = studentService.findStudentByMajor(major);
        return ResponseEntity.ok(dtos);
    }
    
    @Operation(summary = "학생 정보 삭제")
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "학생 정보 수정")
    @PutMapping
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO dto) {
       StudentDTO updateDto = studentService.updateStudent(dto);
        return ResponseEntity.ok(updateDto);
    }

    @Operation(summary = "학생 리스트")
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.findAllStudents();
        return ResponseEntity.ok(students);
    }
}
