package com.itwill.backend_smart_campus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classroom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom {

    @Id
    @Column(name = "classroom_id")
    private Long classroomId;

    @Column(name = "building", length = 100)
    private String building;

    @Column
    (name="room_number")
    private Integer roomNumber;

}