package com.immutable.visitormanagement.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitorDto {

    @NotBlank(message = "please provide your organization name")
    @Length(min = 3, message = "your name must be greater than 2 Characters")
    private String visitorName;

    @NotNull(message = "age is required")
    @Min(15)
    private int age;
    private String gender;
    @Size(max = 10, min = 10, message = "please enter your valid mobile number")
    private String contactNumber;

    @Email(message = "please enter valid email Id")
    private String email;
    @NotNull(message = "please select Visit Type")
    private String typeOfVisit;

    @NotBlank(message = "please provide your organization name")
    private String visitorOrganization;

    private String whomToMeet;

    private String photoPath;

    @NotBlank(message = "please,mention the purpose of meeting")
    private String reasonForMeeting;
}
