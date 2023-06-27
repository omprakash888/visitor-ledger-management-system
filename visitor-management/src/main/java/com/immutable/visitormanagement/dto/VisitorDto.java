package com.immutable.visitormanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class VisitorDto {
    @NotBlank(message = "age is required")
    private int age;
    @NotEmpty
    private String gender;
    @NotEmpty
    @Size(max = 10, min = 10, message = "your mobile number must be 10 digits only")
    private String contactNumber;

    @Email
    private String email;
    @NotEmpty
    private String visitorOrganization;
    private String whomToMeet;
    @NotEmpty
    private String reasonForMeeting;
}
