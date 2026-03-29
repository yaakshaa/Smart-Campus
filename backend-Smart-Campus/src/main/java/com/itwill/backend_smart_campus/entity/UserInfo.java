package com.itwill.backend_smart_campus.entity;

import com.itwill.backend_smart_campus.dto.UserInfoDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "userinfo")
public class UserInfo {

    @Id
    @Column(name = "userinfo_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "userinfo_password", length = 100)
    private String userPassword;

    @Column(name = "userinfo_name", length = 100)
    private String userName;

    @Column(name = "userinfo_email", length = 100, unique = true)
    private String userEmail;

    @Column(name = "userinfo_type", length = 50)
    private String userType;

    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Student student;

    public static UserInfo toEntity(UserInfoDTO userDto) {
        return UserInfo.builder()
                .userId(userDto.getUserId())
                .userPassword(userDto.getUserPassword())
                .userName(userDto.getUserName())
                .userEmail(userDto.getUserEmail())
                .userType(userDto.getUserType())
                .build();
    }
}