package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.CategoryDTO;
import com.itwill.backend_smart_campus.entity.Category;
import com.itwill.backend_smart_campus.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category saved = categoryRepository.save(Category.toEntity(categoryDTO)); // DTO → Entity
        return CategoryDTO.toDto(saved); // Entity → DTO
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDTO::toDto) // Entity → DTO
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategory(Long categoryNo) {
        return categoryRepository.findById(categoryNo)
                .map(CategoryDTO::toDto) // Entity → DTO
                .orElse(null);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryNo, String newCategoryName) {
        Category category = categoryRepository.findById(categoryNo).orElse(null);
        if (category != null) {
            category.setCategoryName(newCategoryName);
            Category updated = categoryRepository.save(category);
            return CategoryDTO.toDto(updated);
        }
        return null;
    }

    @Override
    public Category findCategoryEntity(Long categoryNo) {
        return categoryRepository.findById(categoryNo)
                .orElseThrow(() -> new RuntimeException("카테고리 없음"));
    }

    @Override
    public void deleteCategory(Long categoryNo) {
        categoryRepository.deleteById(categoryNo);
    }
}
