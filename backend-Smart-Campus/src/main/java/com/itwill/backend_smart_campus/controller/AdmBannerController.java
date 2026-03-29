package com.itwill.backend_smart_campus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.backend_smart_campus.dto.AdmBannerDTO;
import com.itwill.backend_smart_campus.dto.AdminDTO;
import com.itwill.backend_smart_campus.service.AdmBannerService;
import com.itwill.backend_smart_campus.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/adminBanner")
@RequiredArgsConstructor

public class AdmBannerController {

    private final AdmBannerService admBannerService;

    @Operation(summary = "배너 등록")
    @PostMapping
    public ResponseEntity<Void> createBanner(@RequestBody AdmBannerDTO dto) {
        admBannerService.createBanner(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "배너 상세보기")
    @GetMapping("/{bannerId}")
    public ResponseEntity<AdmBannerDTO> findBannerById(@PathVariable Long bannerId) {
        AdmBannerDTO dto = admBannerService.findBannerById(bannerId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "배너 삭제")
    @DeleteMapping("/{bannerId}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long bannerId) {
        admBannerService.deleteBanner(bannerId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "배너 수정")
    @PutMapping
    public ResponseEntity<Void> updateBanner(@RequestBody AdmBannerDTO dto) {
        admBannerService.updateBanner(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "배너 전체보기")
    @GetMapping("/banners")
    public ResponseEntity<List<AdmBannerDTO>> findAllAdmBanners() {
        List<AdmBannerDTO> banners = admBannerService.findAllAdmBanners();
        return ResponseEntity.ok(banners);
    }
}
