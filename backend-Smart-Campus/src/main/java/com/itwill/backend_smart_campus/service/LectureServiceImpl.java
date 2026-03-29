package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.LectureDTO;
import com.itwill.backend_smart_campus.entity.*;
import com.itwill.backend_smart_campus.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final TermRepository termRepository;
    private final ClassroomRepository classroomRepository;

    @Override
    public List<LectureDTO> findAllLectures() {
        return lectureRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public LectureDTO findLectureById(Long id) {
        return lectureRepository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public LectureDTO createLecture(LectureDTO dto) {
        // ⭐ 이 부분을 삭제하거나 주석 처리해야 해! ⭐
        // if (dto.getLectureId() == null) {
        //     dto.setLectureId(System.currentTimeMillis()); // 데이터베이스가 ID를 생성하게 할 것이므로 불필요
        // }
        
        // toEntity 메서드에서도 lectureId를 dto.getLectureId()로 설정하는 부분을 제거해야 해.
        // 새로운 엔티티는 ID가 없어야 데이터베이스가 자동 생성함.
        Lecture lecture = Lecture.builder()
                // .lectureId(dto.getLectureId()) // ⭐ 이 줄을 삭제! ⭐
                .professor(getProfessor(dto.getProfessorId()))
                .subject(getSubject(dto.getSubjectNo()))
                .term(getTerm(dto.getTermId()))
                .classroom(getClassroom(dto.getClassroomId()))
                .maxEnrollment(dto.getMaxEnrollment())
                .build();
        
        return toDTO(lectureRepository.save(lecture));
    }

    @Override
    public LectureDTO updateLecture(Long id, LectureDTO dto) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow();

        // 업데이트는 기존 lectureId를 그대로 사용해야 해
        lecture.setProfessor(getProfessor(dto.getProfessorId()));
        lecture.setSubject(getSubject(dto.getSubjectNo()));
        lecture.setTerm(getTerm(dto.getTermId()));
        lecture.setClassroom(getClassroom(dto.getClassroomId()));
        lecture.setMaxEnrollment(dto.getMaxEnrollment());

        return toDTO(lectureRepository.save(lecture));
    }

    @Override
    public void deleteLecture(Long id) {
        lectureRepository.deleteById(id);
    }

    private LectureDTO toDTO(Lecture lecture) {
        return LectureDTO.builder()
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
    }

    // toEntity 메서드는 업데이트 시 사용하고, 생성 시에는 위에처럼 직접 빌더 사용
    // 또는 toEntity 메서드에서 lectureId를 받지 않도록 변경
    private Lecture toEntity(LectureDTO dto) {
        // 이 메서드는 업데이트 로직에 주로 사용하고, 생성 시에는 ID를 null로 둠
        // 만약 ID가 dto에 있어도 새로운 생성 시에는 무시되어야 함.
        return Lecture.builder()
                .lectureId(dto.getLectureId()) // 이 줄은 업데이트 시에는 필요. 생성 시에는 ID를 안 넘기면 됨.
                .professor(getProfessor(dto.getProfessorId()))
                .subject(getSubject(dto.getSubjectNo()))
                .term(getTerm(dto.getTermId()))
                .classroom(getClassroom(dto.getClassroomId()))
                .maxEnrollment(dto.getMaxEnrollment())
                .build();
    }

    private Professor getProfessor(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found"));
    }

    private Subject getSubject(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
    }

    private Term getTerm(Long id) {
        return termRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Term not found"));
    }

    private Classroom getClassroom(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
    }
}