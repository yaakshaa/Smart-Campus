package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.ParkingLotDTO;

public interface ParkingService {
    ParkingLotDTO create(ParkingLotDTO dto);

    ParkingLotDTO getById(Long id);

    List<ParkingLotDTO> getAll();

    ParkingLotDTO update(Long id, ParkingLotDTO dto);

    void delete(Long id);
}
