package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.AdminDTO;

public interface AdminService {

//관리자 등록
AdminDTO createAdmin(AdminDTO adminDTO);

//관리자 상세보기
AdminDTO findAdminById(String adminId);

//관리자 삭제
void deleteAdmin(String adminId);

//관리자 수정
AdminDTO updateAdmin(AdminDTO adminDTO);

//관리자 전체보기 
List<AdminDTO> findAllAdmins();
}
