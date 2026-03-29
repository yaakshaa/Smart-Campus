package com.itwill.backend_smart_campus.entity;

import com.itwill.backend_smart_campus.dto.AdmBannerDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "AdmBanner")

public class AdmBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="banner_id")
    private Long bannerId;

    @Column(name = "banner_title")
    private String bannerTitle;

    @Column(name = "banner_link", length = 500)
    private String bannerLink;

    @Column(name = "banner_order")
    private Integer bannerOrder;

    @Column(name = "banner_image", length = 1000)
    private String bannerImage;

    public static AdmBanner toEntity(AdmBannerDTO admBannerDTO) {
        return AdmBanner.builder()
                        .bannerId(admBannerDTO.getBannerId())
                        .bannerTitle(admBannerDTO.getBannerTitle())
                        .bannerLink(admBannerDTO.getBannerLink())
                        .bannerOrder(admBannerDTO.getBannerOrder())
                        .bannerImage(admBannerDTO.getBannerImage())
                        .build();
    }
}
