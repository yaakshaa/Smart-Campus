package com.itwill.backend_smart_campus.dto;

public class FloorDTO {
    private Long floorId;
    private Long buildingId;
    private Integer floorNumber;

    public FloorDTO() {
    }

    public FloorDTO(Long floorId, Long buildingId, Integer floorNumber) {
        this.floorId = floorId;
        this.buildingId = buildingId;
        this.floorNumber = floorNumber;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
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

}
