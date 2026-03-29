package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.LectureDTO;
import com.itwill.backend_smart_campus.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
@CrossOrigin
public class LectureController {

    private final LectureService lectureService;

    @GetMapping
    public ResponseEntity<List<LectureDTO>> getAllLectures() {
        return ResponseEntity.ok(lectureService.findAllLectures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureDTO> getLectureById(@PathVariable Long id) {
        return ResponseEntity.ok(lectureService.findLectureById(id));
    }

    @PostMapping
    public ResponseEntity<LectureDTO> createLecture(@RequestBody LectureDTO lectureDTO) {
        return ResponseEntity.ok(lectureService.createLecture(lectureDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LectureDTO> updateLecture(@PathVariable Long id, @RequestBody LectureDTO lectureDTO) {
        return ResponseEntity.ok(lectureService.updateLecture(id, lectureDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
        lectureService.deleteLecture(id);
        return ResponseEntity.noContent().build();
    }
}
