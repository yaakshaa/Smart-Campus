package com.itwill.backend_smart_campus.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.itwill.backend_smart_campus.entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO {

    private int reservationId;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String reservationStatus;
    private UserInfoDTO user;
    private StudyRoomDTO studyRoom;

    public static ReservationDTO toDto(Reservation reservationEntity) {
        return ReservationDTO.builder()
        .reservationId(reservationEntity.getReservationId())
        .reservationDate(reservationEntity.getReservationDate())
        .startTime(reservationEntity.getStartTime())
        .endTime(reservationEntity.getEndTime())
        .reservationStatus(reservationEntity.getReservationStatus())
        .studyRoom(StudyRoomDTO.toDto(reservationEntity.getStudyRoom()))
        .user(UserInfoDTO.toDto(reservationEntity.getUser()))
        .build();
    }

    public Reservation toEntity() {
         return Reservation.builder()
        .reservationId(getReservationId())
        .reservationDate(getReservationDate())
        .startTime(getStartTime())
        .endTime(getEndTime())
        .reservationStatus(getReservationStatus())
        .studyRoom(getStudyRoom() != null ? getStudyRoom().toEntity() : null)
        .user(getUser() != null ? getUser().toEntity() : null)
        .build();
    }
}
