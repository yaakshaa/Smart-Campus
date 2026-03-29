package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Enrollment;
import com.itwill.backend_smart_campus.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // 추가된 메서드: 학생으로 수강신청 목록 조회
    List<Enrollment> findByStudent(Student student);
}