package com.itwill.backend_smart_campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.backend_smart_campus.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findByUserId(String userId);

    boolean existsByUserPassword(String userPassword);

    boolean existsByUserEmail(String userEmail);

}