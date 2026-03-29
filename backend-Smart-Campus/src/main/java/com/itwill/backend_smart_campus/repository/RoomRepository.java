package com.itwill.backend_smart_campus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwill.backend_smart_campus.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByBuilding_BuildingId(Long buildingId);

    List<Room> findByFloorNumber(Integer floorNumber);
}
