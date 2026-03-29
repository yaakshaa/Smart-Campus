package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.RestaurantDTO;
import com.itwill.backend_smart_campus.entity.Restaurant;
import com.itwill.backend_smart_campus.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.findAll();
    }

    @PostMapping
    public Restaurant create(@RequestBody RestaurantDTO dto) {
        // DTO → Entity 변환
        Restaurant restaurant = Restaurant.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .build();

        return restaurantService.save(restaurant);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        restaurantService.deleteById(id);
    }
}
