package com.immutable.visitormanagement.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "visitordetails")
public class VisitorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long visitorId;
    private int age;
    private String gender;
    private String contactNumber;
    private String email;
    private String visitorOrganization;
    private String whomToMeet;
    @CreationTimestamp
    private Date inTime;
    @UpdateTimestamp
    private Date outTime;
    private String reasonForMeeting;

}
