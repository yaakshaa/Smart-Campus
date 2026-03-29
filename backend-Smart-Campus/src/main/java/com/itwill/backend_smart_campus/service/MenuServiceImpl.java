package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.MenuDTO;
import com.itwill.backend_smart_campus.entity.Menu;
import com.itwill.backend_smart_campus.entity.Restaurant;
import com.itwill.backend_smart_campus.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public List<MenuDTO> findAllDto() {
        return menuRepository.findAllWithRestaurant()
                .stream()
                .map(MenuDTO::toDto)
                .toList();
    }

    @Override
    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public Menu save(MenuDTO dto) {
        boolean exists = menuRepository.existsByRestaurantAndDateAndMealType(
                dto.getRestaurantId(), dto.getDate(), dto.getMealType());

        if (exists) {
            throw new IllegalArgumentException("같은 식당, 날짜, 식사 시간에는 메뉴를 하나만 등록할 수 있습니다.");
        }

        Menu menu = Menu.builder()
                .foodName(dto.getFoodName())
                .mealType(dto.getMealType())
                .date(dto.getDate())
                .calorie(dto.getCalorie())
                .allergyInfo(dto.getAllergyInfo())
                .restaurant(Restaurant.builder().id(dto.getRestaurantId()).build())
                .build();

        return menuRepository.save(menu);
    }

    @Override
    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public Menu update(Long id, MenuDTO dto) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        menu.setFoodName(dto.getFoodName());
        menu.setMealType(dto.getMealType());
        menu.setDate(dto.getDate());
        menu.setCalorie(dto.getCalorie());
        menu.setAllergyInfo(dto.getAllergyInfo());
        menu.setRestaurant(Restaurant.builder().id(dto.getRestaurantId()).build()); // ✅ 수정 완료

        return menuRepository.save(menu);
    }
}
