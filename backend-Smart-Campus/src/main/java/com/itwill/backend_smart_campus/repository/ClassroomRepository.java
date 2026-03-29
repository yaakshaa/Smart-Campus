package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}