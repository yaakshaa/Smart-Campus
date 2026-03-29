package com.itwill.backend_smart_campus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwill.backend_smart_campus.dto.AdminDTO;
import com.itwill.backend_smart_campus.entity.Admin;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.repository.AdminRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserInfoRepository userInfoRepository;

    //관리자 등록 
    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        UserInfo userInfo = userInfoRepository.findById(adminDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("등록된 관리자가 없습니다: " + adminDTO.getUserId()));
        Admin admin = Admin.builder()
                .adminId(adminDTO.getAdminId())
                .userInfo(userInfo)
                .adminDate(adminDTO.getAdminDate())
                .adminPosition(adminDTO.getAdminPosition())
                .build();
        Admin saved = adminRepository.save(admin);
        return AdminDTO.toDto(saved);
    }

    //관리자 상세보기
    @Override
    public AdminDTO findAdminById(String adminId){
        Admin admin = adminRepository.findById(adminId)
                                     .orElseThrow(()->new RuntimeException("해당 관리자를 찾을 수 없습니다"+adminId));
        return AdminDTO.toDto(admin);
       
    }
    
    //관리자 삭제
    @Override
    public void deleteAdmin(String adminId) {
        adminRepository.deleteById(adminId);
    }
    
    
    //관리자 수정
    @Override
    public AdminDTO updateAdmin(AdminDTO adminDTO) {
        Admin admin = adminRepository.findById(adminDTO.getAdminId())
                                     .orElseThrow(()->new RuntimeException("해당 관리자를 찾을 수 없습니다"+ adminDTO.getAdminId()));
        admin.setAdminDate(adminDTO.getAdminDate());
        admin.setAdminPosition(adminDTO.getAdminPosition());

        Admin saved = adminRepository.save(admin);
        return AdminDTO.toDto(saved);
        
    }
    
    //관리자 전체보기
    @Override
    public List<AdminDTO> findAllAdmins() {
       return adminRepository.findAll().stream().map(AdminDTO::toDto).collect(Collectors.toList());
    }

}
