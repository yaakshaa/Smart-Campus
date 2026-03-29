package com.itwill.backend_smart_campus.controller;

import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.AdminDTO;
import com.itwill.backend_smart_campus.dto.StudentDTO;
import com.itwill.backend_smart_campus.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor

public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "관리자 등록")
    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO dto) {
        AdminDTO savedDto = adminService.createAdmin(dto);
        return ResponseEntity.ok(savedDto);
    }

    @Operation(summary = "관리자 상세보기")
    @GetMapping("/{adminId}")
    public ResponseEntity<AdminDTO> findAdminById(@PathVariable String adminId) {
        AdminDTO dto = adminService.findAdminById(adminId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "관리자 삭제")
    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.ok().build();
    }

     @Operation(summary = "관리자 정보 수정")
    @PutMapping
    public ResponseEntity<AdminDTO> updateAdmin(@RequestBody AdminDTO dto) {
       AdminDTO updateDto = adminService.updateAdmin(dto);
        return ResponseEntity.ok(updateDto);
    }

    @Operation(summary = "관리자 리스트")
    @GetMapping("/admins")
    public ResponseEntity<List<AdminDTO>> findAllAdmins() {
        List<AdminDTO> admins = adminService.findAllAdmins();
        return ResponseEntity.ok(admins);
    }
}