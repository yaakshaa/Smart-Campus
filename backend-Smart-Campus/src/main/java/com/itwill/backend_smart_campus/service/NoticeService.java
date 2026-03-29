package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.NoticeDTO;
import com.itwill.backend_smart_campus.dto.NoticeRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticeService {

    void createNotice(NoticeRequestDTO requestDTO, MultipartFile[] files);

    List<NoticeDTO> getNoticeList();

    NoticeDTO getNoticeById(Long noticeNo);

    void updateNotice(NoticeRequestDTO requestDTO, MultipartFile[] addFiles);

    void deleteNotice(Long noticeNo);
}
