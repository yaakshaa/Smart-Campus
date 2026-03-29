package com.itwill.backend_smart_campus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.backend_smart_campus.dto.ReservationDTO;
import com.itwill.backend_smart_campus.entity.Reservation;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.exception.DuplicateReservationException;
import com.itwill.backend_smart_campus.exception.ReservationNotFoundException;
import com.itwill.backend_smart_campus.exception.UnauthorizedUserException;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.repository.ReservationRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public int reserveRoom(ReservationDTO reservationDTO, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if(!"student".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        boolean isOverlapped = reservationRepository.existsByStudyRoom_StudyRoomIdAndReservationDateAndStartTimeLessThanAndEndTimeGreaterThan(
            reservationDTO.getStudyRoom().getStudyRoomId(), reservationDTO.getReservationDate(), reservationDTO.getEndTime(), reservationDTO.getStartTime());

        if(isOverlapped) {
            throw new DuplicateReservationException("이미 해당 시간에 예약이 존재합니다.");
        }

        Reservation reservation = reservationDTO.toEntity();
        reservation.setReservationStatus("예약중");

        Reservation saveReservation = reservationRepository.save(reservation);
        return saveReservation.getReservationId();
    }

    @Override
    public int cancelReservation(int reservationId, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if(!"student".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        Reservation reserved = reservationRepository.findById(reservationId)
                                                    .orElseThrow(() -> new ReservationNotFoundException("해당 예약 정보를 찾을 수 없습니다."));

        if(!reserved.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedUserException("본인이 예약한 스터디룸만 취소할 수 있습니다.");
        }

        reservationRepository.deleteById(reservationId);
        return reservationId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> findAllReservations(String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if(!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        return reservationRepository.findAll().stream()
                                              .map(ReservationDTO::toDto)
                                              .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO findReservationById(int reservationId, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if(!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        Reservation reservation = reservationRepository.findById(reservationId).get();
        return ReservationDTO.toDto(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> findReservationsByUserId(String targetUserId, String requesterUserId) throws Exception {
        UserInfo requester = userInfoRepository.findById(requesterUserId)
                                          .orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if("admin".equalsIgnoreCase(requester.getUserType())) {                                              
            return reservationRepository.findByUser_UserId(targetUserId).stream()
                                                              .map(ReservationDTO::toDto)
                                                              .collect(Collectors.toList());
        }
        
        if("student".equalsIgnoreCase(requester.getUserType()) && targetUserId.equals(requesterUserId)) {
            return reservationRepository.findByUser_UserId(targetUserId).stream()
                                                              .map(ReservationDTO::toDto)
                                                              .collect(Collectors.toList());
        }
        throw new UnauthorizedUserException("해당 권한이 없습니다.");
    }

}
