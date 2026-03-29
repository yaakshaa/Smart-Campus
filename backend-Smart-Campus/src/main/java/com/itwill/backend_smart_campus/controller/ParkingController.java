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

import com.itwill.backend_smart_campus.dto.ParkingLotDTO;
import com.itwill.backend_smart_campus.service.ParkingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
public class ParkingController {
    private final ParkingService parkingService;

    @PostMapping
    public ResponseEntity<ParkingLotDTO> create(@RequestBody ParkingLotDTO dto) {
        return ResponseEntity.ok((parkingService.create(dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ParkingLotDTO>> getAll() {
        return ResponseEntity.ok(parkingService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingLotDTO> update(@PathVariable Long id, @RequestBody ParkingLotDTO dto) {
        return ResponseEntity.ok(parkingService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
