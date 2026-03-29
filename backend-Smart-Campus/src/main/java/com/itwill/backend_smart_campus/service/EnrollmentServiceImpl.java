package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.EnrollmentDTO;
import com.itwill.backend_smart_campus.dto.LectureDTO;
import com.itwill.backend_smart_campus.entity.Enrollment;
import com.itwill.backend_smart_campus.entity.Lecture;
import com.itwill.backend_smart_campus.entity.Student;
import com.itwill.backend_smart_campus.repository.EnrollmentRepository;
import com.itwill.backend_smart_campus.repository.LectureRepository;
import com.itwill.backend_smart_campus.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("학생을 찾을 수 없습니다."));
        Lecture lecture = lectureRepository.findById(dto.getLectureId())
                .orElseThrow(() -> new RuntimeException("강의를 찾을 수 없습니다."));

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .lecture(lecture)
                .enrollmentDate(LocalDate.now())
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);

        return EnrollmentDTO.builder()
                .enrollmentId(saved.getEnrollmentId())
                .studentId(saved.getStudent().getStudentId())
                .lectureId(saved.getLecture().getLectureId())
                .enrollmentDate(saved.getEnrollmentDate())
                .build();
    }

    @Override
    public EnrollmentDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수강 신청 정보를 찾을 수 없습니다."));
        return EnrollmentDTO.builder()
                .enrollmentId(enrollment.getEnrollmentId())
                .studentId(enrollment.getStudent().getStudentId())
                .lectureId(enrollment.getLecture().getLectureId())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .build();
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(e -> EnrollmentDTO.builder()
                        .enrollmentId(e.getEnrollmentId())
                        .studentId(e.getStudent().getStudentId())
                        .lectureId(e.getLecture().getLectureId())
                        .enrollmentDate(e.getEnrollmentDate())
                        .build())
                .toList();
    }

    @Override
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    // studentId (Long) 대신 userId (String)으로 변경
    public List<EnrollmentDTO> getEnrollmentsByStudentId(String userId) {
        // findById 대신 findByUserInfo_UserId 사용
        Student student = studentRepository.findByUserInfo_UserId(userId)
                .orElseThrow(() -> new RuntimeException("학생을 찾을 수 없습니다."));

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        List<EnrollmentDTO> result = new ArrayList<>();

        for (Enrollment e : enrollments) {
            Lecture lecture = e.getLecture();

            LectureDTO lectureDTO = LectureDTO.builder()
                    .lectureId(lecture.getLectureId())
                    .professorId(lecture.getProfessor().getProfessorId())
                    .professorName(lecture.getProfessor().getUserInfo().getUserName())
                    .subjectNo(lecture.getSubject().getSubjectNo())
                    .subjectName(lecture.getSubject().getSubjectName())
                    .credit(lecture.getSubject().getCredit())
                    .termId(lecture.getTerm().getId())
                    .classroomId(lecture.getClassroom().getClassroomId())
                    .classroomName(lecture.getClassroom().getBuilding() + " " + lecture.getClassroom().getRoomNumber())
                    .maxEnrollment(lecture.getMaxEnrollment())
                    .build();

            EnrollmentDTO enrollmentDTO = EnrollmentDTO.builder()
                    .enrollmentId(e.getEnrollmentId())
                    .studentId(e.getStudent().getStudentId())
                    .lectureId(e.getLecture().getLectureId())
                    .enrollmentDate(e.getEnrollmentDate())
                    .lecture(lectureDTO)
                    .build();

            result.add(enrollmentDTO);
        }

        return result;
    }
}