package com.itwill.backend_smart_campus.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDTO {
    private Long restaurantId; 

    private String name;
    private String location;

    public static RestaurantDTO toDto(com.itwill.backend_smart_campus.entity.Restaurant restaurant) {
        return RestaurantDTO.builder()
                .restaurantId(restaurant.getId()) 
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .build();
    }
}
