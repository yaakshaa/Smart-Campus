package com.itwill.backend_smart_campus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itwill.backend_smart_campus.entity.GradeInfo;

public interface GradeInfoRepository extends JpaRepository<GradeInfo, Long> {

    List<GradeInfo> findByStudent_StudentId(Long studentId);

    List<GradeInfo> findByLecture_LectureId(Long lectureId);

    @Query("""
        SELECT gradeInfo FROM GradeInfo gradeInfo
        JOIN FETCH gradeInfo.lecture lecture
        JOIN FETCH lecture.subject subject
        WHERE gradeInfo.student.studentId = :studentId
        AND lecture.term.id = :termId
    """)
    List<GradeInfo> findByStudentIdAndTermIdWithSubject(Long studentId, Long termId);

    List<GradeInfo> findByStudentStudentIdAndLectureTermId(Long studentId, Long termId);
}