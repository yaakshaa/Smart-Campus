package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.EnrollmentDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO);

    EnrollmentDTO getEnrollmentById(Long id);

    List<EnrollmentDTO> getAllEnrollments();

    void deleteEnrollment(Long id);

    List<EnrollmentDTO> getEnrollmentsByStudentId(String userId);

}
