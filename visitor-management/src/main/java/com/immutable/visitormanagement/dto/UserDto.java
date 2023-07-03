package com.immutable.visitormanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "please provide valid employee name")
    @Length(min = 4, max = 25, message = "your name must be in between 4 and 25 characters")
    private String name;
    @Email(message = "enter valid email Address")
    private String email;
    @NotBlank(message = "please enter valid password")
    @Length(min = 8, max = 16, message = "your name must be in between 4 and 25 characters")
    private String password;


}
