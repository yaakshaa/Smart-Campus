package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ROOM")
@SequenceGenerator(name = "room_seq_gen", sequenceName = "ROOM_SEQ", allocationSize = 1)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq_gen")
    @Column(name = "ROOM_ID")
    private Long roomId;

    @Column(name = "ROOM_NAME", nullable = false, length = 100)
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUILDING_ID", nullable = false)
    private MapBuilding building;

    @Column(name = "FLOOR_NUMBER", nullable = false)
    private Integer floorNumber;

    public Room() {
    }

    public Room(String roomName, MapBuilding building, Integer floorNumber) {
        this.roomName = roomName;
        this.building = building;
        this.floorNumber = floorNumber;
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

    public MapBuilding getBuilding() {
        return building;
    }

    public void setBuilding(MapBuilding building) {
        this.building = building;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

}
