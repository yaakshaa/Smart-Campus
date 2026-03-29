package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.MenuDTO;
import com.itwill.backend_smart_campus.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    List<MenuDTO> findAllDto(); // ✅ 추가됨
    Optional<Menu> findById(Long id);
    Menu save(MenuDTO dto);
    void deleteById(Long id);
    Menu update(Long id, MenuDTO dto);
}
