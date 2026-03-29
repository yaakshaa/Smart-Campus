package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.TermGradeDTO;
import com.itwill.backend_smart_campus.dto.TermGradeSummaryDTO;

public interface TermGradeService {
    List<TermGradeDTO> findAll();
    TermGradeDTO findById(Long Id);
    TermGradeDTO create(TermGradeDTO dto);
    TermGradeDTO update(Long id, TermGradeDTO dto);
    void delete(Long Id);

    TermGradeDTO calculateAndSaveTermGrade(Long studentId, Long termId);
    TermGradeSummaryDTO calculateTermSummary(Long studentId, Long termId);
}
