package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "study_room")
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "study_room_seq_gen")
    @SequenceGenerator(name = "study_room_seq_gen", sequenceName = "study_room_seq", allocationSize = 1)
    @Column(name = "study_room_id")
    private int studyRoomId;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "max_people", nullable = false)
    private int maxPeople;
}
