package com.itwill.backend_smart_campus.repository;

import com.itwill.backend_smart_campus.entity.NoticeFile;
import com.itwill.backend_smart_campus.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long> {

    List<NoticeFile> findByNotice(Notice notice);          // 조회용
    void deleteByNotice(Notice notice);                    // 삭제용
    void deleteByFileNoIn(List<Long> fileNos);             // 일부 파일 삭제 (수정용)
}
