package com.itwill.backend_smart_campus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itwill.backend_smart_campus.entity.UserInfo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("password")
    private String userPassword;

    @JsonProperty("name")
    private String userName;

    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("userType")
    private String userType;;

    public static UserInfoDTO toDto(UserInfo userEntity) {
        return UserInfoDTO.builder()
                .userId(userEntity.getUserId())
                .userPassword(userEntity.getUserPassword())
                .userName(userEntity.getUserName())
                .userEmail(userEntity.getUserEmail())
                .userType(userEntity.getUserType())
                .build();
    }

    public UserInfo toEntity() {
        return UserInfo.builder()
                .userId(getUserId())
                .userPassword(getUserPassword())
                .userName(getUserName())
                .userEmail(getUserEmail())
                .userType(getUserType())
                .build();
    }
}