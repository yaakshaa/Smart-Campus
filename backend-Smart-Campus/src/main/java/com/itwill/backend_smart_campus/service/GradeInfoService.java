package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.GradeInfoDTO;
import com.itwill.backend_smart_campus.dto.TermGradeSummaryDTO;

public interface GradeInfoService {
    // 기본 CRUD
    List<GradeInfoDTO> findAll();

    GradeInfoDTO findById(Long id);

    GradeInfoDTO create(GradeInfoDTO dto);

    GradeInfoDTO update(Long id, GradeInfoDTO dto);

    void delete(Long id);

    List<GradeInfoDTO> findByStudentId(Long studentId);

    List<GradeInfoDTO> findByStudentAndTerm(Long studentId, Long termId);

    TermGradeSummaryDTO calculateTermSummary(Long studentId, Long termId);

    /*
     * 추가 기능
     * List<GradeInfoDTO> findByStudentIdAndTerm(Long studentId, int year, String
     * semester);
     * 
     * BigDecimal calculateGradeScoreAvg(Long studentId);
     * 
     * BigDecimal calculateGradePointAvg(Long studentId);
     * 
     * BigDecimal calculateGradeScoreSum(Long studentId);
     * 
     * BigDecimal calculateGradePointSum(Long studentId);
     * 
     * Map<String, List<GradeInfoDTO>> groupBySubjectType(Long studentId);
     */

}
