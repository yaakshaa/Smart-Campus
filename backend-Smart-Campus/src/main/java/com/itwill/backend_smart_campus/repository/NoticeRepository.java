package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}

