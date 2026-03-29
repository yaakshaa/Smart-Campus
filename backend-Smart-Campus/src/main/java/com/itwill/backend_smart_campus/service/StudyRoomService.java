package com.itwill.backend_smart_campus.service;

import java.util.List;

import com.itwill.backend_smart_campus.dto.StudyRoomDTO;

public interface StudyRoomService {

    int insertStudyRoom(StudyRoomDTO studyRoomDTO, String userId) throws Exception;

    int updateStudyRoom(StudyRoomDTO studyRoomDTO, String userId) throws Exception;

    int deleteStudyRoomById(int studyRoomId, String userId) throws Exception;

    List<StudyRoomDTO> findAllStudyRooms() throws Exception;
    
    StudyRoomDTO findStudyRoomById(int studyRoomId) throws Exception;
}
