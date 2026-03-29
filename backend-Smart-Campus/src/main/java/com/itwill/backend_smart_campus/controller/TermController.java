package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.TermDTO;
import com.itwill.backend_smart_campus.service.TermService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terms")
@RequiredArgsConstructor
public class TermController {

    private final TermService termService;

    // 모든 학기 조회
    @GetMapping
    public ResponseEntity<List<TermDTO>> getAllTerms() {
        return ResponseEntity.ok(termService.findAll());
    }

    // 특정 학기 조회
    @GetMapping("/{id}")
    public ResponseEntity<TermDTO> getTermById(@PathVariable Long id) {
        return ResponseEntity.ok(termService.findById(id));
    }

    // 학기 생성
    @PostMapping
    public ResponseEntity<TermDTO> createTerm(@RequestBody TermDTO termDTO) {
        return ResponseEntity.ok(termService.create(termDTO));
    }

    // 학기 수정
    @PutMapping("/{id}")
    public ResponseEntity<TermDTO> updateTerm(@PathVariable Long id, @RequestBody TermDTO termDTO) {
        return ResponseEntity.ok(termService.update(id, termDTO));
    }

    // 학기 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerm(@PathVariable Long id) {
        termService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
