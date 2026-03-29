package com.itwill.backend_smart_campus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.MapBuildingDTO;
import com.itwill.backend_smart_campus.service.MapBuildingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class MapBuildingController {
    private final MapBuildingService buildingService;

    @PostMapping
    public ResponseEntity<MapBuildingDTO> create(@RequestBody MapBuildingDTO dto) {
        MapBuildingDTO created = buildingService.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MapBuildingDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(buildingService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<MapBuildingDTO>> getAll() {
        return ResponseEntity.ok(buildingService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MapBuildingDTO> update(@PathVariable Long id, @RequestBody MapBuildingDTO dto) {
        return ResponseEntity.ok(buildingService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        buildingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
