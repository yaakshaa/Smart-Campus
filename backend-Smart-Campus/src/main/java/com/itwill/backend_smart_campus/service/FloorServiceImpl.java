package com.itwill.backend_smart_campus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwill.backend_smart_campus.dto.FloorDTO;
import com.itwill.backend_smart_campus.entity.Floor;
import com.itwill.backend_smart_campus.repository.FloorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FloorServiceImpl implements FloorService {
    private final FloorRepository floorRepository;

    @Override
    public List<FloorDTO> getFloorsByBuilding(Long buildingId) {
        return floorRepository.findByMapBuilding_BuildingId(buildingId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private FloorDTO toDto(Floor floor) {
        return new FloorDTO(floor.getFloorId(), floor.getMapBuilding().getBuildingId(), floor.getFloorNumber());
    }
}
