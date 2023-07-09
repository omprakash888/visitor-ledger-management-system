package com.immutable.visitormanagement.response;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadResponse {

    private Long visitorId;
    private String visitorName;
    private int age;
    private String gender;
    private String contactNumber;
    private String email;
    private String typeOfVisit;
    private String visitorOrganization;
    private String whomToMeet;
    private String date;
    private String inTime;
    private String outTime;
    private String reasonForMeeting;
}
