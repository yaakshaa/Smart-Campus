package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.ReservationDTO;

public interface ReservationService {

    int reserveRoom(ReservationDTO reservationDTO, String userId) throws Exception;

    int cancelReservation(int reservationId, String userId) throws Exception;

    List<ReservationDTO> findAllReservations(String userId) throws Exception;

    ReservationDTO findReservationById(int reservationId, String userId) throws Exception;

    List<ReservationDTO> findReservationsByUserId (String targetUserId, String requesterUserId) throws Exception;
}
