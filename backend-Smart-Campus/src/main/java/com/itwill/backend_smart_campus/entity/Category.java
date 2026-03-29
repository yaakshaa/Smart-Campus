package com.itwill.backend_smart_campus.entity;

import com.itwill.backend_smart_campus.dto.CategoryDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long categoryNo; // 카테고리 번호 (PK)

    @Column(name = "category_name", nullable = false, unique = true, length = 100)
    private String categoryName; // 카테고리 이름

    public static Category toEntity(CategoryDTO dto) {
    return Category.builder()
            .categoryNo(dto.getCategoryNo()) // 등록 시 null, 수정 시 PK 전달
            .categoryName(dto.getCategoryName())
            .build();
}
}
