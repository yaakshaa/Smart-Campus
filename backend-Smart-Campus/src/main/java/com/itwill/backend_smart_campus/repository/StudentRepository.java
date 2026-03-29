package com.itwill.backend_smart_campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itwill.backend_smart_campus.entity.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByUserInfo_UserNameContaining(String userName);

    List<Student> findByStudentMajorContaining(String studentMajor);

    Optional<Student> findByUserInfo_UserId(String userId);

    List<Student> findByUserInfo_UserType(String userType);
}
