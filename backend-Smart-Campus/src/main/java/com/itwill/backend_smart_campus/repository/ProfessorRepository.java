package com.itwill.backend_smart_campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwill.backend_smart_campus.entity.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Professor findByUserInfo_UserId(String userId);
}