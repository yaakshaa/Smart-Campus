package com.itwill.backend_smart_campus.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.backend_smart_campus.dto.TermGradeDTO;
import com.itwill.backend_smart_campus.dto.TermGradeSummaryDTO;
import com.itwill.backend_smart_campus.entity.GradeInfo;
import com.itwill.backend_smart_campus.entity.Student;
import com.itwill.backend_smart_campus.entity.Term;
import com.itwill.backend_smart_campus.entity.TermGrade;
import com.itwill.backend_smart_campus.repository.GradeInfoRepository;
import com.itwill.backend_smart_campus.repository.StudentRepository;
import com.itwill.backend_smart_campus.repository.TermGradeRepository;
import com.itwill.backend_smart_campus.repository.TermRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TermGradeServiceImpl implements TermGradeService {

    private final TermGradeRepository termGradeRepository;
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final GradeInfoRepository gradeInfoRepository;

    @Override
    public List<TermGradeDTO> findAll() {
        return termGradeRepository.findAll().stream()
                .map(TermGradeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TermGradeDTO findById(Long id) {
        return termGradeRepository.findById(id)
                .map(TermGradeDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("TermGrade not found with id: " + id));
    }

    @Override
    public TermGradeDTO create(TermGradeDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Term term = termRepository.findById(dto.getTermId())
                .orElseThrow(() -> new RuntimeException("Term not found with id: " + dto.getTermId()));

        TermGrade termGrade = TermGrade.builder()
                .student(student)
                .term(term)
                .attemptedCredits(dto.getAttemptedCredits())
                .earnedCredits(dto.getEarnedCredits())
                .gradeScoreTotal(dto.getGradeScoreTotal())
                .gradeScoreAvg(dto.getGradeScoreAvg())
                .gradePointSum(dto.getGradePointSum())
                .gradePointAvg(dto.getGradePointAvg())
                .build();

        return TermGradeDTO.fromEntity(termGradeRepository.save(termGrade));
    }

    @Override
    public TermGradeDTO update(Long id, TermGradeDTO dto) {
        TermGrade termGrade = termGradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TermGrade not found with id: " + id));

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Term term = termRepository.findById(dto.getTermId())
                .orElseThrow(() -> new RuntimeException("Term not found with id: " + dto.getTermId()));

        termGrade.setStudent(student);
        termGrade.setTerm(term);
        termGrade.setAttemptedCredits(dto.getAttemptedCredits());
        termGrade.setEarnedCredits(dto.getEarnedCredits());
        termGrade.setGradeScoreTotal(dto.getGradeScoreTotal());
        termGrade.setGradeScoreAvg(dto.getGradeScoreAvg());
        termGrade.setGradePointSum(dto.getGradePointSum());
        termGrade.setGradePointAvg(dto.getGradePointAvg());

        return TermGradeDTO.fromEntity(termGradeRepository.save(termGrade));
    }

    @Override
    public void delete(Long id) {
        termGradeRepository.deleteById(id);
    }

    @Override
    public TermGradeDTO calculateAndSaveTermGrade(Long studentId, Long termId) {
        // [ 관련 gradeinfo 목록 조회 JOIN FETCH ]
        List<GradeInfo> gradeInfos = gradeInfoRepository.findByStudentIdAndTermIdWithSubject(studentId, termId);

        int attemptedCredits = 0;
        int earnedCredits = 0;
        BigDecimal gradeScoreTotal = BigDecimal.ZERO;
        BigDecimal gradePointSum = BigDecimal.ZERO;

        for (GradeInfo gi : gradeInfos) {
            int credit = gi.getLecture().getSubject().getCredit();
            BigDecimal score = gi.getGradeScore();
            BigDecimal point = gi.getGradePoint();

            attemptedCredits += credit;

            if (point.compareTo(BigDecimal.valueOf(1.0)) >= 0) {
                earnedCredits += credit;
            }

            gradeScoreTotal = gradeScoreTotal.add(score.multiply(BigDecimal.valueOf(credit)));
            gradePointSum = gradePointSum.add(point.multiply(BigDecimal.valueOf(credit)));
        }

        BigDecimal gradeScoreAvg = attemptedCredits == 0 ? BigDecimal.ZERO
                : gradeScoreTotal.divide(BigDecimal.valueOf(attemptedCredits), 2, RoundingMode.HALF_UP);

        BigDecimal gradePointAvg = attemptedCredits == 0 ? BigDecimal.ZERO
                : gradePointSum.divide((BigDecimal.valueOf(attemptedCredits)), 2, RoundingMode.HALF_UP);

        // [ 연관 객체 조회 ]
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new RuntimeException("Term not found with id: " + termId));

        // [ termgrade 저장 ]
        TermGrade termGrade = TermGrade.builder()
                .student(student)
                .term(term)
                .attemptedCredits(attemptedCredits)
                .earnedCredits(earnedCredits)
                .gradeScoreTotal(gradeScoreTotal)
                .gradePointAvg(gradePointAvg)
                .gradePointSum(gradePointSum)
                .gradePointAvg(gradePointAvg)
                .build();

        termGradeRepository.save(termGrade);

        return TermGradeDTO.fromEntity(termGrade);
    }

    @Override
    public TermGradeSummaryDTO calculateTermSummary(Long studentId, Long termId) {
        List<GradeInfo> gradeInfos = (termId != null)
                ? gradeInfoRepository.findByStudentStudentIdAndLectureTermId(studentId, termId)
                : gradeInfoRepository.findByStudent_StudentId(studentId);

        int attemptedCredits = 0;
        int earnedCredits = 0;
        BigDecimal gradeScoreSum = BigDecimal.ZERO;
        BigDecimal gradePointSum = BigDecimal.ZERO;

        for (GradeInfo grade : gradeInfos) {
            attemptedCredits += grade.getAttemptedCredits();
            earnedCredits += grade.getEarnedCredits();
            gradeScoreSum = gradeScoreSum.add(grade.getGradeScore());
            gradePointSum = gradePointSum.add(grade.getGradePoint());
        }

        int count = gradeInfos.size();
        BigDecimal gradeScoreAvg = count > 0 ? gradeScoreSum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        BigDecimal gradePointAvg = count > 0 ? gradePointSum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return TermGradeSummaryDTO.builder()
                .attemptedCredits(attemptedCredits)
                .earnedCredits(earnedCredits)
                .gradeScoreTotal(gradeScoreSum)
                .gradeScoreAvg(gradeScoreAvg)
                .gradePointSum(gradePointSum)
                .gradePointAvg(gradePointAvg)
                .build();
    }
}
