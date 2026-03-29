package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.TermGradeDTO;
import com.itwill.backend_smart_campus.dto.TermGradeSummaryDTO;
import com.itwill.backend_smart_campus.service.TermGradeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/termgrades")
@RequiredArgsConstructor
public class TermGradeController {

    private final TermGradeService termGradeService;

    @GetMapping
    public List<TermGradeDTO> getAllTermGrades() {
        return termGradeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermGradeDTO> getTermGrade(@PathVariable Long id) {
        return ResponseEntity.ok(termGradeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TermGradeDTO> createTermGrade(@RequestBody TermGradeDTO dto) {
        return ResponseEntity.ok(termGradeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TermGradeDTO> updateTermGrade(@PathVariable Long id, @RequestBody TermGradeDTO dto) {
        return ResponseEntity.ok(termGradeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermGrade(@PathVariable Long id) {
        termGradeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/calculate")
    public ResponseEntity<TermGradeDTO> calculateAndSaveTermGrade(
            @RequestParam Long studentId,
            @RequestParam Long termId) {

        TermGradeDTO result = termGradeService.calculateAndSaveTermGrade(studentId, termId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/summary")
    public TermGradeSummaryDTO calculateTermSummary(
            @RequestParam Long studentId,
            @RequestParam(required = false) Long termId) {
        return termGradeService.calculateTermSummary(studentId, termId);
    }
}
