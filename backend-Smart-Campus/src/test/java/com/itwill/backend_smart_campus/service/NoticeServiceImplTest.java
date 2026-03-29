package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.NoticeDTO;
import com.itwill.backend_smart_campus.dto.NoticeRequestDTO;
import com.itwill.backend_smart_campus.service.NoticeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoticeServiceImplTest {

    @Autowired
    private NoticeService noticeService;

    // ⚠️ DB에 미리 존재하는 공지사항 번호를 사용하세요
    private static final Long EXISTING_NOTICE_ID = 10005L;

    @Test
    @Order(1)
    public void getNotice_존재하는_공지_조회() {
        NoticeDTO notice = noticeService.getNoticeById(EXISTING_NOTICE_ID);
        System.out.println("📄 조회된 공지 제목: " + notice.getNoticeTitle());
        System.out.println("📄 조회된 공지 내용: " + notice.getNoticeContent());

        assertThat(notice).isNotNull();
        assertThat(notice.getNoticeNo()).isEqualTo(EXISTING_NOTICE_ID);
    }

    @Test
    @Order(2)
    @Transactional
    public void updateNotice_기존공지_수정() {
        NoticeRequestDTO requestDTO = NoticeRequestDTO.builder()
                .noticeNo(EXISTING_NOTICE_ID)
                .adminId("admin01") // 등록한 공지의 admin_id와 일치해야 함
                .noticeTitle("🛠️ 수정된 제목")
                .noticeContent("🛠️ 수정된 내용입니다.")
                .build();

        noticeService.updateNotice(requestDTO, null);

        NoticeDTO updated = noticeService.getNoticeById(EXISTING_NOTICE_ID);
        System.out.println("✅ 수정된 제목: " + updated.getNoticeTitle());
        assertThat(updated.getNoticeTitle()).isEqualTo("🛠️ 수정된 제목");
    }

    @Test
    @Order(3)
    @Transactional
    public void deleteNotice_기존공지_삭제() {
        noticeService.deleteNotice(EXISTING_NOTICE_ID);

        boolean isPresent = noticeService.getNoticeList().stream()
                .anyMatch(n -> n.getNoticeNo().equals(EXISTING_NOTICE_ID));

        System.out.println("🗑️ 삭제 성공 여부(false여야 성공): " + isPresent);
        assertThat(isPresent).isFalse();
    }
}
