package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}