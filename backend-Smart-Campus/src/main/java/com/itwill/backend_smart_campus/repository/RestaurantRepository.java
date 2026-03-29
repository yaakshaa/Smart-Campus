package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // 메뉴 정보까지 함께 가져오는 fetch join 쿼리
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus")
    List<Restaurant> findAllWithMenus();
}
