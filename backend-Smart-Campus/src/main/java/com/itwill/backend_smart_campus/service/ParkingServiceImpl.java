package com.itwill.backend_smart_campus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.backend_smart_campus.dto.ParkingLotDTO;
import com.itwill.backend_smart_campus.entity.ParkingLot;
import com.itwill.backend_smart_campus.repository.ParkingLotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {
    private final ParkingLotRepository repository;

    @Override
    public ParkingLotDTO create(ParkingLotDTO dto) {
        ParkingLot e = new ParkingLot();
        e.setLotName(dto.getLotName());
        e.setLatitude(dto.getLatitude());
        e.setLongitude(dto.getLongitude());
        e.setCapacity(dto.getCapacity());
        e.setAvailableSpace(dto.getAvailableSpace());
        ParkingLot saved = repository.save(e);
        return mapDto(saved);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);

    }

    @Override
    public List<ParkingLotDTO> getAll() {
        List<ParkingLotDTO> list = new ArrayList<>();
        for (ParkingLot e : repository.findAll())
            list.add(mapDto(e));
        return list;
    }

    @Override
    public ParkingLotDTO getById(Long id) {
        ParkingLot e = repository.findById(id).orElseThrow(() -> new RuntimeException("Parking lot not found"));
        return mapDto(e);
    }

    @Override
    public ParkingLotDTO update(Long id, ParkingLotDTO dto) {
        ParkingLot e = repository.findById(id).orElseThrow(() -> new RuntimeException("Parking lot not found"));
        e.setLotName(dto.getLotName());
        e.setLatitude(dto.getLatitude());
        e.setLongitude(dto.getLongitude());
        e.setCapacity(dto.getCapacity());
        e.setAvailableSpace(dto.getAvailableSpace());
        return mapDto(repository.save(e));
    }

    private ParkingLotDTO mapDto(ParkingLot e) {
        ParkingLotDTO dto = new ParkingLotDTO();
        dto.setParkingLotId(e.getParkingLotId());
        dto.setLotName(e.getLotName());
        dto.setLatitude(e.getLatitude());
        dto.setLongitude(e.getLongitude());
        dto.setCapacity(e.getCapacity());
        dto.setAvailableSpace(e.getAvailableSpace());
        return dto;
    }
}
