package com.itwill.backend_smart_campus.service;

import java.beans.Transient;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.itwill.backend_smart_campus.dto.StudentDTO;
import com.itwill.backend_smart_campus.entity.Student;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.repository.StudentRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserInfoRepository userInfoRepository;

    // 학생 정보 등록
    @Override
    @Transactional
    public StudentDTO createStudent(StudentDTO dto) {
        UserInfo userInfo = userInfoRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("등록되지 않은 사용자입니다" + dto.getUserId()));

        Student student = Student.builder()
                .studentId(dto.getStudentId())
                .userInfo(userInfo)
                .studentMajor(dto.getStudentMajor())
                .studentGrade(dto.getStudentGrade())
                .studentPhone(dto.getStudentPhone())
                .studentStatus(dto.getStudentStatus())
                .build();

        Student saved = studentRepository.save(student);

        return StudentDTO.toDto(saved);
    }

    // 학생상세보기(학번)
    @Override
    public StudentDTO findStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .map(StudentDTO::toDto)
                .orElseThrow(() -> new RuntimeException("학생을 찾을 수 없습니다:" + studentId));
    }

    // 학생상세보기(이름)
    @Override
    public List<StudentDTO> findStudentByName(String userName) {
        return studentRepository.findByUserInfo_UserNameContaining(userName)
                .stream()
                .map(StudentDTO::toDto)
                .collect(Collectors.toList());
    }

    // 학생상세보기(전공)
    @Override
    public List<StudentDTO> findStudentByMajor(String studentMajor) {
        return studentRepository.findByStudentMajorContaining(studentMajor)
                .stream()
                .map(StudentDTO::toDto)
                .collect(Collectors.toList());
    }

    // 학생 정보 삭제
    @Override
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    // 학생리스트 조회
    @Override
    public List<StudentDTO> findAllStudents() {
        List<Student> students = studentRepository.findByUserInfo_UserType("STUDENT");
        return students.stream().map(StudentDTO::toDto).collect(Collectors.toList());
    }

    // 학생 정보수정
    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        Student student = studentRepository.findById(studentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("학생을 찾을 수 없습니다: " + studentDTO.getStudentId()));

        student.setStudentMajor(studentDTO.getStudentMajor());
        student.setStudentGrade(studentDTO.getStudentGrade());
        student.setStudentPhone(studentDTO.getStudentPhone());
        student.setStudentStatus(studentDTO.getStudentStatus());

        Student saved = studentRepository.save(student);
        return StudentDTO.toDto(saved);
    }

    // 유저ID로 학생 조회
    @Override
    public Student findByUserId(String userId) {
        return studentRepository.findByUserInfo_UserId(userId)
                .orElseThrow(() -> new RuntimeException("해당 userId로 학생을 찾을 수 없습니다: " + userId));
    }

    // 유저ID로 회원가입시 학생 테이블 생성
    @Override
    public void createByUserId(String userId) {
        UserInfo user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 userId로 유저를 찾을 수 없습니다: " + userId));
        
        //학생 정보 임시 적용
        Student student = Student.builder()
                .userInfo(user) 
                .studentStatus("재학")
                .studentMajor("미정")
                .studentGrade(1)
                .studentPhone("000-0000-0000")
                .build();

        studentRepository.save(student);
    }

}
