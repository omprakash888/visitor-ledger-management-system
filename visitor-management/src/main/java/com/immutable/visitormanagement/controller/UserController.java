package com.immutable.visitormanagement.controller;


import com.immutable.visitormanagement.dto.UserDto;
import com.immutable.visitormanagement.entity.ConfirmationToken;
import com.immutable.visitormanagement.service.ConfirmTokenService;
import com.immutable.visitormanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ConfirmTokenService confirmationTokenService;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody UserDto userDto) throws SQLIntegrityConstraintViolationException {
        userService.signUp(userDto);
        return new ResponseEntity<>("Thank you for registering. Your registration has been successful. Please wait for your account to be activated. Once the activation process is complete, we will notify you via email.", HttpStatus.CREATED);
    }

    @PostMapping("/activateAccount/{token}")
    public ResponseEntity<String> activateAccount(@PathVariable String token) {
        String response = this.confirmationTokenService.verify(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
