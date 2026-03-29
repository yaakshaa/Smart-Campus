package com.itwill.backend_smart_campus.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwill.backend_smart_campus.dto.AdmBannerDTO;
import com.itwill.backend_smart_campus.entity.AdmBanner;
import com.itwill.backend_smart_campus.repository.AdmBannerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdmBannerServiceImpl implements AdmBannerService{

    private final AdmBannerRepository admBannerRepository;

    //배너 등록
    @Override
    @Transactional
    public void createBanner(AdmBannerDTO admBannerDTO) {
       admBannerRepository.save(AdmBanner.toEntity(admBannerDTO));
    }
    //배너 상세보기
    @Override
    public AdmBannerDTO findBannerById(Long bannerId) {
      return AdmBannerDTO.toDto(admBannerRepository.findById(bannerId).get());
    }
    //배너 삭제
    @Override
    public void deleteBanner(Long bannerId) {
        admBannerRepository.deleteById(bannerId);
    }
    //배너 수정
    @Override
    public void updateBanner(AdmBannerDTO admBannerDTO) {
      admBannerRepository.save(AdmBanner.toEntity(admBannerDTO));
    }
    //베너 전체보기
    @Override
    public List<AdmBannerDTO> findAllAdmBanners() {
       return admBannerRepository.findAll().stream().map(AdmBannerDTO::toDto).collect(Collectors.toList());
    }



    



    

}
