package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Room;

public class RoomDTO {

    private Long roomId;
    private String roomName;
    private Long buildingId;
    private Integer floorNumber;

    public RoomDTO() {
    }

    public RoomDTO(Long roomId, String roomName, Long buildingId, Integer floorNumber) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.buildingId = buildingId;
        this.floorNumber = floorNumber;
    }

    public static RoomDTO fromEntity(Room room) {
        return new RoomDTO(room.getRoomId(), room.getRoomName(),
                room.getBuilding() != null ? room.getBuilding().getBuildingId() : null, room.getFloorNumber());
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    @Override
    public String toString() {
        return "RoomDTO [roomId=" + roomId + ", roomName=" + roomName + ", buildingId=" + buildingId + ", floorNumber="
                + floorNumber + "]";
    }

}
