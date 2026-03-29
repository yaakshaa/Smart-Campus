package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.ChatMainMenu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatMainMenuRepository extends JpaRepository<ChatMainMenu, Integer> {
     List<ChatMainMenu> findAllByOrderByMenuDisplayOrder();
}