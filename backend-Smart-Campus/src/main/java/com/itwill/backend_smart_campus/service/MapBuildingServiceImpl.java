package com.itwill.backend_smart_campus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.backend_smart_campus.dto.FloorDTO;
import com.itwill.backend_smart_campus.dto.MapBuildingDTO;
import com.itwill.backend_smart_campus.entity.Floor;
import com.itwill.backend_smart_campus.entity.MapBuilding;
import com.itwill.backend_smart_campus.repository.MapBuildingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapBuildingServiceImpl implements MapBuildingService {
    private final MapBuildingRepository repository;

    @Override
    public MapBuildingDTO create(MapBuildingDTO dto) {
        MapBuilding entity = new MapBuilding();
        mapDtoToEntity(dto, entity);
        MapBuilding saved = repository.save(entity);
        return mapEntityToDto(saved);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);

    }

    @Override
    public List<MapBuildingDTO> getAll() {
        List<MapBuildingDTO> dtos = new ArrayList<>();
        for (MapBuilding entity : repository.findAll()) {
            dtos.add(mapEntityToDto(entity));
        }
        return dtos;
    }

    @Override
    public MapBuildingDTO getById(Long id) {
        return repository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new RuntimeException("Building not found"));
    }

    @Override
    public MapBuildingDTO update(Long id, MapBuildingDTO dto) {
        MapBuilding existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found"));
        mapDtoToEntity(dto, existing);
        return mapEntityToDto(repository.save(existing));
    }

    private MapBuildingDTO mapEntityToDto(MapBuilding entity) {
        MapBuildingDTO dto = new MapBuildingDTO();
        dto.setBuildingId(entity.getBuildingId());
        dto.setBuildingName(entity.getBuildingName());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setDescription(entity.getDescription());
        List<FloorDTO> fDtos = new ArrayList<>();
        if (entity.getFloorPlans() != null) {
            for (Floor fp : entity.getFloorPlans()) {
                FloorDTO fpd = new FloorDTO();
                fpd.setFloorId(fp.getFloorId());
                fpd.setBuildingId(entity.getBuildingId());
                fpd.setFloorNumber(fp.getFloorNumber());
                fDtos.add(fpd);
            }
        }
        dto.setFloorPlans(fDtos);
        return dto;
    }

    private void mapDtoToEntity(MapBuildingDTO dto, MapBuilding entity) {
        entity.setBuildingName(dto.getBuildingName());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setDescription(dto.getDescription());
        List<Floor> fps = new ArrayList<>();
        if (dto.getFloorPlans() != null) {
            for (FloorDTO fpd : dto.getFloorPlans()) {
                Floor fp = new Floor();
                fp.setFloorId(fpd.getFloorId());
                fp.setFloorNumber(fpd.getFloorNumber());
                fp.setMapBuilding(entity);
                fps.add(fp);
            }
        }
        entity.setFloorPlans(fps);
    }

}
