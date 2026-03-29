package com.itwill.backend_smart_campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwill.backend_smart_campus.entity.ParkingLot;


@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

}
