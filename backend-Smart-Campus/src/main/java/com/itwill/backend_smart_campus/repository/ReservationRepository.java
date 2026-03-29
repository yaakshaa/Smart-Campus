package com.itwill.backend_smart_campus.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.backend_smart_campus.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{

    List<Reservation> findByUser_UserId (String userId);

    boolean existsByStudyRoom_StudyRoomIdAndReservationDateAndStartTimeLessThanAndEndTimeGreaterThan(int studyRoomId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime);
}
