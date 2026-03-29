package com.itwill.backend_smart_campus.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapBuildingDTO {
    private Long buildingId;
    private String buildingName;
    private Double latitude;
    private Double longitude;
    private String description;
    private List<FloorDTO> floorPlans;
}
