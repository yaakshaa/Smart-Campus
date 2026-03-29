package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "FLOOR")
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long floorId; // number(19)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private MapBuilding mapBuilding;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber; // number(4)

    public Floor() {
    }

    public Floor(MapBuilding mapBuilding, Integer floorNumber) {
        this.mapBuilding = mapBuilding;
        this.floorNumber = floorNumber;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public MapBuilding getMapBuilding() {
        return mapBuilding;
    }

    public void setMapBuilding(MapBuilding mapBuilding) {
        this.mapBuilding = mapBuilding;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

}
