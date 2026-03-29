package com.itwill.backend_smart_campus.dto;

import java.sql.Date;

import com.itwill.backend_smart_campus.entity.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AdminDTO {

private String adminId;
private String userId;
private Date adminDate;
private String adminPosition;

public static AdminDTO toDto(Admin adminEntity) {
    return AdminDTO.builder()
            .adminId(adminEntity.getAdminId())
            .userId(adminEntity.getUserInfo().getUserId())
            .adminDate(adminEntity.getAdminDate())
            .adminPosition(adminEntity.getAdminPosition())
            .build();
}

}
