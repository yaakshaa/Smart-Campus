package com.itwill.backend_smart_campus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwill.backend_smart_campus.dto.RoomDTO;
import com.itwill.backend_smart_campus.entity.MapBuilding;
import com.itwill.backend_smart_campus.entity.Room;
import com.itwill.backend_smart_campus.repository.MapBuildingRepository;
import com.itwill.backend_smart_campus.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepo;
    private final MapBuildingRepository buildingRepo;

    public RoomServiceImpl(RoomRepository roomRepo, MapBuildingRepository buildingRepo) {
        this.roomRepo = roomRepo;
        this.buildingRepo = buildingRepo;
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepo.findAll().stream().map(RoomDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepo.findById(id).orElseThrow(() -> new RuntimeException("Room not found:" + id));
        return RoomDTO.fromEntity(room);
    }

    @Override
    public List<RoomDTO> getRoomsByBuilding(Long buildingId) {
        return roomRepo.findByBuilding_BuildingId(buildingId).stream().map(RoomDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getRoomsByFloor(Integer floorNumber) {
        return roomRepo.findByFloorNumber(floorNumber).stream().map(RoomDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public RoomDTO createRoom(RoomDTO dto) {
        Room room = new Room(dto.getRoomName(),
                buildingRepo.findById(dto.getBuildingId())
                        .orElseThrow(() -> new RuntimeException("Building not found:" + dto.getBuildingId())),
                dto.getFloorNumber());
        Room saved = roomRepo.save(room);
        return RoomDTO.fromEntity(saved);
    }

    @Override
    public RoomDTO updateRoom(Long id, RoomDTO dto) {
        Room room = roomRepo.findById(id).orElseThrow(() -> new RuntimeException("Room not found:" + id));
        room.setRoomName(dto.getRoomName());
        room.setFloorNumber(dto.getFloorNumber());
        MapBuilding building = buildingRepo.findById(dto.getBuildingId())
                .orElseThrow(() -> new RuntimeException("Building not found:" + dto.getBuildingId()));
        room.setBuilding(building);
        Room updated = roomRepo.save(room);
        return RoomDTO.fromEntity(updated);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepo.deleteById(id);

    }

}
