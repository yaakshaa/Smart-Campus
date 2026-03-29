package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.StudentDTO;
import com.itwill.backend_smart_campus.entity.Student;

public interface StudentService {

    //학생 정보 등록
    StudentDTO createStudent(StudentDTO studentDTO);

    //학생 상세보기(학번 조회)
    StudentDTO findStudentById(Long studentId);

    //학생 이름으로 조회
    List<StudentDTO> findStudentByName(String userName);

    //학생 전공으로 조회
    List<StudentDTO> findStudentByMajor(String studentMajor);

    //학생 정보 삭제 
    void deleteStudent(Long studentId);

    //학생 리스트 보기
    List<StudentDTO> findAllStudents();
     
    //학생 정보 수정 
    StudentDTO updateStudent(StudentDTO studentDTO);

    //userId로 student 조회
    Student findByUserId(String userId);

    //userId로 stduent 생성 (회원가입 시 생성)
     void createByUserId(String userId);
}
  