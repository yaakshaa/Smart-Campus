package com.itwill.backend_smart_campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwill.backend_smart_campus.entity.MapBuilding;

@Repository
public interface MapBuildingRepository extends JpaRepository<MapBuilding, Long> {

}
