package com.immutable.visitormanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long visitorId;
    private String visitorName;
    private int age;
    private String gender;
    private String contactNumber;
    private String email;
    private String typeOfVisit;
    private String visitorOrganization;
    private String whomToMeet;
    @CreationTimestamp
    private Date inTime;
    private Date outTime;
    private String reasonForMeeting;
    @Column(length  = 10000)
    private String photoPath;
    @ManyToOne
    private Employee employee;
}
