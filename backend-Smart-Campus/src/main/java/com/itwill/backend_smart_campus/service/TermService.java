package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.TermDTO;
import com.itwill.backend_smart_campus.dto.TermGradeSummaryDTO;

public interface TermService {
    List<TermDTO> findAll();
    TermDTO findById(Long id);
    TermDTO create(TermDTO dto);
    TermDTO update(Long id, TermDTO dto);
    void delete(Long id);
    TermGradeSummaryDTO calculateTermSummary(Long studentId, Long termId);
}
