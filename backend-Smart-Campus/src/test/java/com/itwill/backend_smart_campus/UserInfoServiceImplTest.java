package com.itwill.backend_smart_campus;

import com.itwill.backend_smart_campus.dto.UserInfoDTO;
import com.itwill.backend_smart_campus.service.UserInfoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserInfoServiceImplTest {

    /* @Autowired
    private UserInfoService userInfoService;

    @Test
    public void testUpdate() throws Exception {
        UserInfoDTO updatedUser = UserInfoDTO.builder()
                .userId("userb")
                .userPassword("modify")
                .userName("이름수정")
                .userEmail("update@gmail.com")
                .userType("professor") // 혹은 "ADMIN", "PROFESSOR" 등 원래 값에 맞춰서
                .build();

        userInfoService.update(updatedUser);
    }

    
    @Test
    public void testRemove() throws Exception {
        userInfoService.remove("userb");
    } */
    
}