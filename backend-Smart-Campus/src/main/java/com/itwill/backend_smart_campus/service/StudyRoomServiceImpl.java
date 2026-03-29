package com.itwill.backend_smart_campus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.backend_smart_campus.dto.StudyRoomDTO;
import com.itwill.backend_smart_campus.entity.StudyRoom;
import com.itwill.backend_smart_campus.entity.UserInfo;
import com.itwill.backend_smart_campus.exception.StudyRoomNotFoundException;
import com.itwill.backend_smart_campus.exception.UnauthorizedUserException;
import com.itwill.backend_smart_campus.exception.UserNotFoundException;
import com.itwill.backend_smart_campus.repository.StudyRoomRepository;
import com.itwill.backend_smart_campus.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyRoomServiceImpl implements StudyRoomService{
    private final StudyRoomRepository studyRoomRepository;
    private final UserInfoRepository userInfoRepository;
    
    @Override
    public int insertStudyRoom(StudyRoomDTO studyRoomDTO, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if(!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }
        
        StudyRoom saveRoom = studyRoomRepository.save(studyRoomDTO.toEntity());
        return saveRoom.getStudyRoomId();
    }

    @Override
    public int updateStudyRoom(StudyRoomDTO studyRoomDTO, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if(!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        StudyRoom updateRoom = studyRoomRepository.save(studyRoomDTO.toEntity());
        return updateRoom.getStudyRoomId();
    }

    @Override
    public int deleteStudyRoomById(int studyRoomId, String userId) throws Exception {
        UserInfo user = userInfoRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 계정이 없습니다."));

        if(!"admin".equalsIgnoreCase(user.getUserType())) {
            throw new UnauthorizedUserException("해당 권한이 없습니다.");
        }

        studyRoomRepository.deleteById(studyRoomId);
        return studyRoomId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudyRoomDTO> findAllStudyRooms() throws Exception {
        return studyRoomRepository.findAll().stream()
                                            .map(StudyRoomDTO::toDto)
                                            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudyRoomDTO findStudyRoomById(int studyRoomId) throws Exception {
        StudyRoom room = studyRoomRepository.findById(studyRoomId).orElseThrow(() -> new StudyRoomNotFoundException("해당 열람실이 존재하지 않습니다."));
        return StudyRoomDTO.toDto(room);
    }
}
