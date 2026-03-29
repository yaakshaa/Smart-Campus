package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.MapBuildingDTO;

public interface MapBuildingService {
    MapBuildingDTO create(MapBuildingDTO dto);

    MapBuildingDTO getById(Long id);

    List<MapBuildingDTO> getAll();

    MapBuildingDTO update(Long id, MapBuildingDTO dto);

    void delete(Long id);
}
