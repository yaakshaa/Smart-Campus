package com.itwill.backend_smart_campus.controller;

import com.itwill.backend_smart_campus.dto.MenuDTO;
import com.itwill.backend_smart_campus.entity.Menu;
import com.itwill.backend_smart_campus.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // ✅ DTO 반환으로 변경
    @GetMapping
    public List<MenuDTO> getAll() {
        return menuService.findAllDto();
    }

    @PostMapping
    public Menu create(@RequestBody MenuDTO menuDTO) {
        return menuService.save(menuDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        menuService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Menu update(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        return menuService.update(id, menuDTO);
    }
}
