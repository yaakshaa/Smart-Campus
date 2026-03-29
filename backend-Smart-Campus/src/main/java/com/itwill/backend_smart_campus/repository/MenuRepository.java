package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant")
    List<Menu> findAllWithRestaurant();

    // MenuRepository.java
    @Query("SELECT COUNT(m) > 0 FROM Menu m WHERE m.restaurant.id = :restaurantId AND m.date = :date AND m.mealType = :mealType")
    boolean existsByRestaurantAndDateAndMealType(Long restaurantId, LocalDate date, String mealType);

}
