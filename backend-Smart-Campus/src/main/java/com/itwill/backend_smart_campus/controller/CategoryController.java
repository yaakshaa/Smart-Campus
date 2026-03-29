package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.CategoryDTO;
import com.itwill.backend_smart_campus.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 등록")
    @PostMapping
    public ResponseEntity<Response> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO created = categoryService.createCategory(categoryDTO);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.CREATED_CATEGORY);
        response.setMessage(ResponseMessage.CREATED_CATEGORY);
        response.setData(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "전체 카테고리 조회")
    @GetMapping
    public ResponseEntity<Response> getAllCategories() {
        List<CategoryDTO> list = categoryService.getAllCategories();

        Response response = new Response();
        response.setStatus(ResponseStatusCode.READ_ALL_CATEGORY);
        response.setMessage(ResponseMessage.READ_ALL_CATEGORY);
        response.setData(list);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카테고리 단건 조회")
    @GetMapping("/{categoryNo}")
    public ResponseEntity<Response> getCategory(@PathVariable Long categoryNo) {
        CategoryDTO dto = categoryService.getCategory(categoryNo);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.READ_CATEGORY);
        response.setMessage(ResponseMessage.READ_CATEGORY);
        response.setData(dto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카테고리 이름 수정")
    @PutMapping("/{categoryNo}")
    public ResponseEntity<Response> updateCategory(
            @PathVariable Long categoryNo,
            @RequestParam String newCategoryName
    ) {
        CategoryDTO updated = categoryService.updateCategory(categoryNo, newCategoryName);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.UPDATE_CATEGORY);
        response.setMessage(ResponseMessage.UPDATE_CATEGORY);
        response.setData(updated);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "카테고리 삭제")
    @DeleteMapping("/{categoryNo}")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long categoryNo) {
        categoryService.deleteCategory(categoryNo);

        Response response = new Response();
        response.setStatus(ResponseStatusCode.DELETE_CATEGORY);
        response.setMessage(ResponseMessage.DELETE_CATEGORY);
        response.setData(categoryNo);

        return ResponseEntity.ok(response);
    }
}