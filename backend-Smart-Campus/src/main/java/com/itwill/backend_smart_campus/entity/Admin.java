package com.itwill.backend_smart_campus.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name="Admin")


public class Admin {

    @Id
    @Column(name="admin_id")
    private String adminId; //

    @ManyToOne
    @JoinColumn(name = "userinfo_id")
    private UserInfo userInfo;

    @Column(name="admin_date")
    private Date adminDate;

    @Column(name="admin_position")
    private String adminPosition;
} 
