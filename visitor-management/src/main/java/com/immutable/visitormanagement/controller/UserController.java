package com.immutable.visitormanagement.controller;


import com.immutable.visitormanagement.dto.UserDto;
import com.immutable.visitormanagement.response.MessageResponse;
import com.immutable.visitormanagement.service.ConfirmTokenService;
import com.immutable.visitormanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

import static com.immutable.visitormanagement.constants.Constants.*;

@RestController
public class UserController {

    private final UserService userService;
    private final ConfirmTokenService confirmationTokenService;


    @Autowired
    public UserController(UserService userService, ConfirmTokenService confirmationTokenService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @PostMapping(CREATE_URL_USER)
    public ResponseEntity<MessageResponse> registration(@RequestBody UserDto userDto) throws SQLIntegrityConstraintViolationException {
        userService.signUp(userDto);
        MessageResponse userCreatedresponse = new MessageResponse();
        userCreatedresponse.setMessage("created Succssfully");
        return new ResponseEntity<>(userCreatedresponse,HttpStatus.CREATED);
    }

    @GetMapping(ACTIVATE_ACCOUNT_URL_USER)
    public ResponseEntity<String> activateAccount(@PathVariable String token) {
        String response = this.confirmationTokenService.verify(token);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(FORGOT_PASSWORD_URL_USER)
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        String response = this.userService.forgotPassword(email);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping(RESET_PASSWORD_URL_USER)
    public ResponseEntity<String> resetPassword(@PathVariable("token") String token, @PathVariable("password") String password) {
        String response = this.userService.resetPassword(token,password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
