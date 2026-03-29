package com.itwill.backend_smart_campus.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.FloorDTO;
import com.itwill.backend_smart_campus.service.FloorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/floors")
@RequiredArgsConstructor
public class FloorController {
    private final FloorService floorService;

    public List<FloorDTO> getFloors(@RequestParam Long buildingId) {
        return floorService.getFloorsByBuilding(buildingId);
    }
}
