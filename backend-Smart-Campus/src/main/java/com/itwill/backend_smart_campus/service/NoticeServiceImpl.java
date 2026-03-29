package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.*;
import com.itwill.backend_smart_campus.entity.*;
import com.itwill.backend_smart_campus.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;
    private final AdminRepository adminRepository;

    private final String uploadBasePath = "C:/test_upload";
    // private final String uploadBasePath = System.getProperty("user.dir") + "/uploads/notice";



    @Override
    @Transactional
    public void createNotice(NoticeRequestDTO requestDTO, MultipartFile[] files) {

        Admin admin = adminRepository.findById(requestDTO.getAdminId())
        .orElseThrow(() -> new RuntimeException("존재하지 않는 관리자입니다."));

        Notice notice = Notice.builder()
                .noticeTitle(requestDTO.getNoticeTitle())
                .noticeContent(requestDTO.getNoticeContent())
                .admin(admin)
                .noticeCount(0)
                .noticeCreatedAt(LocalDateTime.now())
                .build();

        noticeRepository.save(notice);

        if (files != null) {

            File dir = new File(uploadBasePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String filePath = uploadBasePath + "/" + fileName;

                try {
                    file.transferTo(new File(filePath));
                } catch (Exception e) {
                    throw new RuntimeException("파일 저장 실패: " + fileName, e);
                }

                NoticeFile noticeFile = NoticeFile.builder()
                        .notice(notice)
                        .fileName(fileName)
                        .filePath(filePath)
                        .build();

                noticeFileRepository.save(noticeFile);
            }
        }
    }

    @Override
    public List<NoticeDTO> getNoticeList() {
        return noticeRepository.findAll().stream()
                .map(NoticeDTO::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NoticeDTO getNoticeById(Long noticeNo) {
        Notice notice = noticeRepository.findById(noticeNo)
                .orElseThrow(() -> new RuntimeException("공지사항이 존재하지 않습니다."));

        // 조회수 증가
        notice.setNoticeCount(notice.getNoticeCount() + 1);
        noticeRepository.save(notice);

        List<NoticeFileDTO> files = noticeFileRepository.findByNotice(notice).stream()
                .map(NoticeFileDTO::toDto)
                .collect(Collectors.toList());

        NoticeDTO dto = NoticeDTO.toDto(notice);
        dto.setFiles(files);
        return dto;
    }

    @Override
    @Transactional
    public void updateNotice(NoticeRequestDTO requestDTO, MultipartFile[] addFiles) {
        Notice notice = noticeRepository.findById(requestDTO.getNoticeNo())
                .orElseThrow(() -> new RuntimeException("공지사항이 존재하지 않습니다."));

        notice.setNoticeTitle(requestDTO.getNoticeTitle());
        notice.setNoticeContent(requestDTO.getNoticeContent());

        // 기존 파일 중 일부 삭제
        if (requestDTO.getDeleteFileIds() != null) {
            noticeFileRepository.deleteAllById(List.of(requestDTO.getDeleteFileIds()));
        }

        // 새로운 파일 추가
        if (addFiles != null) {
            File dir = new File(uploadBasePath);
            if (!dir.exists()) {
                dir.mkdirs();  // ✅ 경로가 없으면 생성
            }

            for (MultipartFile file : addFiles) {
                String fileName = file.getOriginalFilename();
                String filePath = uploadBasePath + "/" + fileName;

                System.out.println("저장 경로 확인: " + filePath);
                
                try {
                    file.transferTo(new File(filePath));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("파일 저장 실패: " + filePath, e);
                }

                NoticeFile noticeFile = NoticeFile.builder()
                        .notice(notice)
                        .fileName(fileName)
                        .filePath(filePath)
                        .build();

                noticeFileRepository.save(noticeFile);
            }
        }

        noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeNo) {
        Notice notice = noticeRepository.findById(noticeNo)
                .orElseThrow(() -> new RuntimeException("공지사항이 존재하지 않습니다."));

        // 첨부파일 먼저 삭제
        noticeFileRepository.deleteByNotice(notice);

        // 공지사항 삭제
        noticeRepository.delete(notice);
    }
}
