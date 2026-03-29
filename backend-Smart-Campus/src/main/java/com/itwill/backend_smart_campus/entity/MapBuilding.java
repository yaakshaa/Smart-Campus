package com.itwill.backend_smart_campus.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MAPBUILDING")
public class MapBuilding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private Long buildingId; // number(19)

    @Column(name = "building_name", nullable = false)
    private String buildingName; // varchar2(100)

    @Column(name = "latitude", nullable = false)
    private Double latitude; // number(9,6)

    @Column(name = "longitude", nullable = false)
    private Double longitude; // number(9,6)

    @Column(name = "description", length = 255)
    private String description; // varchar2(255)

    @OneToMany(mappedBy = "mapBuilding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Floor> floorPlans;
}
