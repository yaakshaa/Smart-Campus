package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.SubjectDTO;

import java.util.List;

public interface SubjectService {

    List<SubjectDTO> findAllSubjects();

    SubjectDTO findSubjectById(Long id);

    SubjectDTO createSubject(SubjectDTO subjectDTO);

    SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO);

    void deleteSubject(Long id);
}
