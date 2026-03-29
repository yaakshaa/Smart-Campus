package com.itwill.backend_smart_campus.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.backend_smart_campus.dto.GradeInfoDTO;
import com.itwill.backend_smart_campus.dto.TermGradeSummaryDTO;
import com.itwill.backend_smart_campus.entity.GradeInfo;
import com.itwill.backend_smart_campus.entity.Lecture;
import com.itwill.backend_smart_campus.entity.Student;
import com.itwill.backend_smart_campus.repository.GradeInfoRepository;
import com.itwill.backend_smart_campus.repository.LectureRepository;
import com.itwill.backend_smart_campus.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GradeInfoServiceImpl implements GradeInfoService {

        private final GradeInfoRepository gradeInfoRepository;
        private final StudentRepository studentRepository;
        private final LectureRepository lectureRepository;

        @Override
        public List<GradeInfoDTO> findAll() {
                return gradeInfoRepository.findAll()
                                .stream()
                                .map(GradeInfoDTO::fromEntity)
                                .collect(Collectors.toList());
        }

        @Override
        public List<GradeInfoDTO> findByStudentId(Long studentId) {
                return gradeInfoRepository.findByStudent_StudentId(studentId)
                                .stream()
                                .map(GradeInfoDTO::fromEntity)
                                .collect(Collectors.toList());
        }

        @Override
        public GradeInfoDTO findById(Long id) {
                GradeInfo gradeInfo = gradeInfoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("GradeInfo not found"));
                return GradeInfoDTO.fromEntity(gradeInfo);
        }

        @Override
        public GradeInfoDTO create(GradeInfoDTO dto) {

                // [ student, lecture 조회 ]
                Student student = studentRepository.findById(dto.getStudentId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Student not found with id: " + dto.getStudentId()));
                Lecture lecture = lectureRepository.findById(dto.getLectureId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Lecture not found with id: " + dto.getLectureId()));

                // [ Entity 생성 ]
                GradeInfo gradeInfo = GradeInfo.builder()
                                .student(student)
                                .lecture(lecture)
                                .gradeScore(dto.getGradeScore())
                                .gradePoint(dto.getGradePoint())
                                .gradeLetter(dto.getGradeLetter())
                                .retakeFlag(dto.getRetakeFlag())
                                .build();

                // [ 저장 및 반환 ]
                GradeInfo saved = gradeInfoRepository.save(gradeInfo);
                return GradeInfoDTO.fromEntity(saved);
        }

        @Override
        public GradeInfoDTO update(Long id, GradeInfoDTO dto) {
                // [ 기존 GradeInfo 조회 ]
                GradeInfo gradeInfo = gradeInfoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("GradeInfo not found with id: " + id));

                // [ 관련 엔티티 조회 ]
                Student student = studentRepository.findById(dto.getStudentId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Student not found with id: " + dto.getStudentId()));

                Lecture lecture = lectureRepository.findById(dto.getLectureId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Lecture not found with id: " + dto.getLectureId()));

                // [ 필드 수정 ]
                gradeInfo.setStudent(student);
                gradeInfo.setLecture(lecture);
                gradeInfo.setGradeScore(dto.getGradeScore());
                gradeInfo.setGradePoint(dto.getGradePoint());
                gradeInfo.setGradeLetter(dto.getGradeLetter());
                gradeInfo.setRetakeFlag(dto.getRetakeFlag());

                // [ 저장 및 반환 ]
                GradeInfo updated = gradeInfoRepository.save(gradeInfo);
                return GradeInfoDTO.fromEntity(updated);
        }

        @Override
        public void delete(Long id) {
                gradeInfoRepository.deleteById(id);
        }

        @Override
        public List<GradeInfoDTO> findByStudentAndTerm(Long studentId, Long termId) {
                return gradeInfoRepository.findByStudentStudentIdAndLectureTermId(studentId, termId)
                                .stream()
                                .map(GradeInfoDTO::fromEntity)
                                .collect(Collectors.toList());
        }

        @Override
        public TermGradeSummaryDTO calculateTermSummary(Long studentId, Long termId) {
                List<GradeInfo> gradeInfos = (termId != null)
                                ? gradeInfoRepository.findByStudentStudentIdAndLectureTermId(studentId, termId)
                                : gradeInfoRepository.findByStudent_StudentId(studentId);

        int attempted = 0;
        int earned = 0;
        BigDecimal scoreTotal = BigDecimal.ZERO;
        BigDecimal pointSum = BigDecimal.ZERO;

        for (GradeInfo g : gradeInfos) {
            int credit = g.getLecture().getSubject().getCredit();
            attempted += credit;
            if (g.getGradePoint() != null && g.getGradePoint().compareTo(BigDecimal.ZERO) > 0) {
                earned += credit;
            }
            if (g.getGradeScore() != null) scoreTotal = scoreTotal.add(g.getGradeScore());
            if (g.getGradePoint() != null) pointSum = pointSum.add(g.getGradePoint());
        }

        int count = gradeInfos.size();
        BigDecimal scoreAvg = count > 0 ? scoreTotal.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        BigDecimal pointAvg = count > 0 ? pointSum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        return TermGradeSummaryDTO.builder()
                .attemptedCredits(attempted)
                .earnedCredits(earned)
                .gradeScoreTotal(scoreTotal)
                .gradeScoreAvg(scoreAvg)
                .gradePointSum(pointSum)
                .gradePointAvg(pointAvg)
                .build();
    }
}
