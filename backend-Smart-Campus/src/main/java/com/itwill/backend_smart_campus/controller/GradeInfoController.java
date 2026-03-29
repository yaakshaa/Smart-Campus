package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.GradeInfoDTO;
import com.itwill.backend_smart_campus.dto.TermGradeSummaryDTO;
import com.itwill.backend_smart_campus.service.GradeInfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grade-info")
@RequiredArgsConstructor
public class GradeInfoController {

    private final GradeInfoService gradeInfoService;

    @Operation(summary = "전체 성적 조회")
    @GetMapping
    public List<GradeInfoDTO> findAll() {
        return gradeInfoService.findAll();
    }

    @Operation(summary = "ID로 성적 조회")
    @GetMapping("/{id}")
    public GradeInfoDTO findById(@PathVariable Long id) {
        return gradeInfoService.findById(id);
    }

    @Operation(summary = "성적 생성")
    @PostMapping
    public GradeInfoDTO create(@RequestBody GradeInfoDTO dto) {
        return gradeInfoService.create(dto);
    }

    @Operation(summary = "성적 수정")
    @PutMapping("/{id}")
    public GradeInfoDTO update(@PathVariable Long id, @RequestBody GradeInfoDTO dto) {
        return gradeInfoService.update(id, dto);
    }

    @Operation(summary = "성적 삭제")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        gradeInfoService.delete(id);
    }

    @GetMapping("/filter")
    public List<GradeInfoDTO> getGradeInfosByStudentAndTerm(
            @RequestParam Long studentId,
            @RequestParam Long termId) {
        return gradeInfoService.findByStudentAndTerm(studentId, termId);
    }

    @GetMapping("/summary")
    public TermGradeSummaryDTO getTermGradeSummary(
            @RequestParam Long studentId,
            @RequestParam(required = false) Long termId) {
        return gradeInfoService.calculateTermSummary(studentId, termId);
    }

    /*
     * @Operation(summary = "학생ID별 과목유형 그룹 조회")
     * 
     * @GetMapping("/group/{studentId}")
     * public Map<String, List<GradeInfoDTO>> groupBySubjectType(@PathVariable Long
     * studentId) {
     * return gradeInfoService.groupBySubjectType(studentId);
     * }
     * 
     * @Operation(summary = "학기 요약 성적 계산")
     * 
     * @GetMapping("/summary")
     * public TermGradeSummaryDTO calculateTermSummary(
     * 
     * @RequestParam Long studentId,
     * 
     * @RequestParam Integer year,
     * 
     * @RequestParam String semester) {
     * return gradeInfoService.calculateTermSummary(studentId, year, semester);
     * }
     */
}
