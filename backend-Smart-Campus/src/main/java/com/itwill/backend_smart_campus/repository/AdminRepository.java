package com.itwill.backend_smart_campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.backend_smart_campus.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

}
