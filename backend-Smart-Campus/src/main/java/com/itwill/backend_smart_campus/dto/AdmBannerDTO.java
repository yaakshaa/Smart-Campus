package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.AdmBanner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AdmBannerDTO {

    private Long bannerId;
    private String bannerTitle;
    private String bannerLink;
    private Integer bannerOrder;
    private String bannerImage;

      public static AdmBannerDTO toDto(AdmBanner admBannerEntity) {
        return AdmBannerDTO.builder()
                        .bannerId(admBannerEntity.getBannerId())
                        .bannerTitle(admBannerEntity.getBannerTitle())
                        .bannerLink(admBannerEntity.getBannerLink())
                        .bannerOrder(admBannerEntity.getBannerOrder())
                        .bannerImage(admBannerEntity.getBannerImage())
                        .build();
    }
}
    