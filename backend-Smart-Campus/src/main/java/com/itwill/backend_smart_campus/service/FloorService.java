package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.FloorDTO;

public interface FloorService {
    List<FloorDTO> getFloorsByBuilding(Long buildingId);
}
