package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PARKING_LOT")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingLotId; // number(19)

    @Column(name = "parking_lot_name", nullable = false, length = 100)
    private String lotName; // varchar2(100)

    @Column(name = "latitude", nullable = false)
    private Double latitude; // number(9,6)

    @Column(name = "longitude", nullable = false)
    private Double longitude; // number(9,6)

    @Column(name = "capacity", nullable = false)
    private Integer capacity; // number(5)

    @Column(name = "available_space", nullable = false)
    private Integer availableSpace; // number(5)
}
