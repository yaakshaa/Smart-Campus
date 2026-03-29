package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
