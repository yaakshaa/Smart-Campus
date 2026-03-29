package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
