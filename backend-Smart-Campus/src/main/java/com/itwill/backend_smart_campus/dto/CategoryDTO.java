package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Long categoryNo;
    private String categoryName;

    public static CategoryDTO toDto(Category category) {
        return CategoryDTO.builder()
                .categoryNo(category.getCategoryNo())
                .categoryName(category.getCategoryName())
                .build();
    }
}
