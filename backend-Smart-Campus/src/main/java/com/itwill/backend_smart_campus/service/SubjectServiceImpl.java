package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.SubjectDTO;
import com.itwill.backend_smart_campus.entity.Subject;
import com.itwill.backend_smart_campus.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public List<SubjectDTO> findAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDTO findSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("과목이 존재하지 않습니다. ID: " + id));
        return convertToDTO(subject);
    }

    @Override
    public SubjectDTO createSubject(SubjectDTO dto) {
        Subject subject = convertToEntity(dto);
        return convertToDTO(subjectRepository.save(subject));
    }

    @Override
    public SubjectDTO updateSubject(Long id, SubjectDTO dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("과목이 존재하지 않습니다. ID: " + id));

        subject.setSubjectName(dto.getSubjectName());
        subject.setSubjectType(dto.getSubjectType());
        subject.setCredit(dto.getCredit());

        return convertToDTO(subjectRepository.save(subject));
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    private SubjectDTO convertToDTO(Subject subject) {
        return SubjectDTO.builder()
                .subjectNo(subject.getSubjectNo())
                .subjectName(subject.getSubjectName())
                .subjectType(subject.getSubjectType())
                .credit(subject.getCredit())
                .build();
    }

    private Subject convertToEntity(SubjectDTO dto) {
        return Subject.builder()
                .subjectNo(dto.getSubjectNo()) 
                .subjectName(dto.getSubjectName())
                .subjectType(dto.getSubjectType())
                .credit(dto.getCredit())
                .build();
    }
}
