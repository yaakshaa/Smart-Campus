package com.itwill.backend_smart_campus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.ReservationDTO;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(summary = "스터디룸예약(학생만 가능))")
    @PostMapping
    public ResponseEntity<Response> createReservation(@RequestBody ReservationDTO reservationDTO, HttpSession session) throws Exception {
        String userId = (String) session.getAttribute("sUserInfo");
        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        int reservationId = reservationService.reserveRoom(reservationDTO, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATED_RESERVATION);
        response.setMessage(ResponseMessage.CREATED_RESERVATION);
        response.setData(reservationId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "스터디룸취소(학생만 가능)")
    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<Response> cancelReservation(@PathVariable int reservationId, HttpSession session) throws Exception {
        String userId = (String) session.getAttribute("sUserInfo");
        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        int cancelId = reservationService.cancelReservation(reservationId, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CANCEL_RESERVATION);
        response.setMessage(ResponseMessage.CANCEL_RESERVATION);
        response.setData(cancelId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "전체예약조회(관리자만 가능)")
    @GetMapping
    public ResponseEntity<Response> getAllReservations(HttpSession session) throws Exception {
        String userId = (String) session.getAttribute("sUserInfo");
        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        List<ReservationDTO> reservations = reservationService.findAllReservations(userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.RESERVATION_SUCCESS);
        response.setMessage(ResponseMessage.RESERVATION_SUCCESS);
        response.setData(reservations);

        return ResponseEntity.ok(response);        
    }

    @Operation(summary = "특정예약정보조회(관리자만 가능)")
    @GetMapping("/{reservationId}")
    public ResponseEntity<Response> getReservationById(@PathVariable int reservationId, HttpSession session) throws Exception {
        String userId = (String) session.getAttribute("sUserInfo");
        if(userId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }

        ReservationDTO reservationDTO = reservationService.findReservationById(reservationId, userId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.RESERVATION_SUCCESS);
        response.setMessage(ResponseMessage.RESERVATION_SUCCESS);
        response.setData(reservationDTO);

        return ResponseEntity.ok(response);      
    }

    @Operation(summary = "사용자별예약목록조회(본인 혹은 관리자만 가능)")
    @GetMapping("/user/{targetUserId}")
    public ResponseEntity<Response> getReservationsByUserId(@PathVariable String targetUserId, HttpSession session) throws Exception {
        String requesterUserId = (String) session.getAttribute("sUserInfo");
        if(requesterUserId == null) {
            throw new UserNotFoundException("로그인이 필요합니다.");
        }
        
        List<ReservationDTO> reservations = reservationService.findReservationsByUserId(targetUserId, requesterUserId);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.RESERVATION_SUCCESS);
        response.setMessage(ResponseMessage.RESERVATION_SUCCESS);
        response.setData(reservations);

        return ResponseEntity.ok(response);    
    }

}
