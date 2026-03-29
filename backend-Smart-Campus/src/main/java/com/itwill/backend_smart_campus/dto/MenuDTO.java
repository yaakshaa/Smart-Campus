package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Menu;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDTO {
    private Long menuId;
    private Long restaurantId; // ✅ 이 필드명 정확해야 함

    private String foodName;
    private String mealType;
    private LocalDate date;
    private Integer calorie;
    private String allergyInfo;

    public static MenuDTO toDto(Menu menu) {
        return MenuDTO.builder()
                .menuId(menu.getId())
                .restaurantId(menu.getRestaurant() != null ? menu.getRestaurant().getId() : null)
                .foodName(menu.getFoodName())
                .mealType(menu.getMealType())
                .date(menu.getDate())
                .calorie(menu.getCalorie())
                .allergyInfo(menu.getAllergyInfo())
                .build();
    }
}
