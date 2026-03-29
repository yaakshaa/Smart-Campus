package com.itwill.backend_smart_campus.service;

import java.util.List;
import com.itwill.backend_smart_campus.dto.AdmBannerDTO;

public interface AdmBannerService {

    // 배너 등록
    void createBanner(AdmBannerDTO admBannerDTO);

    // 배너 상세보기
    AdmBannerDTO findBannerById(Long bannerId);

    // 배너 삭제
    void deleteBanner(Long bannerId);

    // 배너 수정
    void updateBanner(AdmBannerDTO admBannerDTO);

    // 배너 전체보기
    List<AdmBannerDTO> findAllAdmBanners();

}
