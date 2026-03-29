package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
    
 