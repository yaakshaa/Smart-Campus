package com.itwill.backend_smart_campus.dto;

import com.itwill.backend_smart_campus.entity.StudyRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyRoomDTO {

    private int studyRoomId;
    private String roomName;
    private int maxPeople;

    public static StudyRoomDTO toDto(StudyRoom studyRoomEntity) {
        return StudyRoomDTO.builder()
        .studyRoomId(studyRoomEntity.getStudyRoomId())
        .roomName(studyRoomEntity.getRoomName())
        .maxPeople(studyRoomEntity.getMaxPeople())
        .build();
    }

    public StudyRoom toEntity() {
        return StudyRoom.builder()
        .studyRoomId(getStudyRoomId())
        .roomName(getRoomName())
        .maxPeople(getMaxPeople())
        .build();
    }
}
