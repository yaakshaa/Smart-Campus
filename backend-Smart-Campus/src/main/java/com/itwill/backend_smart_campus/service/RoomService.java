package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.RoomDTO;

public interface RoomService {

    /* 전체 강의실/사무실 목록 조회 */
    List<RoomDTO> getAllRooms();

    /* 특정 층(floorId)에 속한 강의실/사무실 목록 조회 */
    List<RoomDTO> getRoomsByFloor(Integer floorNumber);

    List<RoomDTO> getRoomsByBuilding(Long buildingId);

    /* 단일 강의실/사무실 상세 조회 */
    RoomDTO getRoomById(Long id);

    /* 강의실/사무실 생성 */
    RoomDTO createRoom(RoomDTO dto);

    /* 강의실/사무실 정보 수정 */
    RoomDTO updateRoom(Long id, RoomDTO dto);

    /* 강의실/사무실 삭제 */
    void deleteRoom(Long id);
}
