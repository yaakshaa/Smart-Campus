package com.itwill.backend_smart_campus.service;

import com.itwill.backend_smart_campus.dto.LectureDTO;

import java.util.List;

public interface LectureService {

    List<LectureDTO> findAllLectures();
    
    LectureDTO findLectureById(Long id);
    
    LectureDTO createLecture(LectureDTO lectureDTO);
    
    LectureDTO updateLecture(Long id, LectureDTO lectureDTO);
    
    void deleteLecture(Long id);

}
