package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.entity.Category;
import com.itwill.backend_smart_campus.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategory(Long categoryNo);

    CategoryDTO updateCategory(Long categoryNo, String newCategoryName);

    void deleteCategory(Long categoryNo);

    Category findCategoryEntity(Long categoryNo);

}
