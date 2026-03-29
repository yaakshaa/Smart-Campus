package com.itwill.backend_smart_campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.backend_smart_campus.entity.Term;

public interface TermRepository extends JpaRepository<Term, Long> {
}
