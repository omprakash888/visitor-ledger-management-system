package com.immutable.visitormanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @NotEmpty(message = "please enter your employee Id")
    @Length(min = 8, max = 8,message = "only 8 characters allowed")
    private long EmployeeId;

    @NotBlank(message = "please provide valid employee name")
    @Length(min = 4, max = 25, message = "your name must be in between 4 and 25 characters")
    private String EmployeeName;
}
