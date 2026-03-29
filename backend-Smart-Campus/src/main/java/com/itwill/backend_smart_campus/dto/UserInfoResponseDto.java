package com.itwill.backend_smart_campus.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String name;
    private String email;
    private String role;
    private Long studentId;
}
