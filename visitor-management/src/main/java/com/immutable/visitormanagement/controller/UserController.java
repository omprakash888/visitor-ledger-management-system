package com.immutable.visitormanagement.controller;


import com.immutable.visitormanagement.dto.UserDto;
import com.immutable.visitormanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public void registration(@RequestBody UserDto userDto) {
        userService.createAdmin(userDto);
    }
}
