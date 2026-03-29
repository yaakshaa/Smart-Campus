package com.itwill.backend_smart_campus.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.itwill.backend_smart_campus.entity.Floor;

public interface FloorRepository extends JpaRepository<Floor, Long> {
    List<Floor> findByMapBuilding_BuildingId(Long buildingId);
}
