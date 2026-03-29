package com.itwill.backend_smart_campus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.backend_smart_campus.dto.TermDTO;
import com.itwill.backend_smart_campus.dto.TermGradeSummaryDTO;
import com.itwill.backend_smart_campus.entity.Term;
import com.itwill.backend_smart_campus.repository.TermRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;

    @Override
    public List<TermDTO> findAll() {
        return termRepository.findAll().stream()
                .map(TermDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TermDTO findById(Long id) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Term not found with id: " + id));
        return TermDTO.fromEntity(term);
    }

    @Override
    public TermDTO create(TermDTO dto) {
        Term term = Term.builder()
                .academicYear(dto.getAcademicYear())
                .semester(dto.getSemester())
                .build();
        return TermDTO.fromEntity(termRepository.save(term));
    }

    @Override
    public TermDTO update(Long id, TermDTO dto) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Term not found with id: " + id));

        term.setAcademicYear(dto.getAcademicYear());
        term.setSemester(dto.getSemester());

        return TermDTO.fromEntity(termRepository.save(term));
    }

    @Override
    public void delete(Long id) {
        termRepository.deleteById(id);
    }
    
    //이거 추가함
    @Override
    public TermGradeSummaryDTO calculateTermSummary(Long studentId, Long termId) {
        // TODO: 여기에 실제 계산 로직 구현
        return null; // 임시로 null 반환. 실제로는 DTO 만들어서 반환
    }
}
