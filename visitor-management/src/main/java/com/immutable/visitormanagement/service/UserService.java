package com.immutable.visitormanagement.service;

import com.immutable.visitormanagement.dto.UserDto;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface UserService {

    public void signUp(UserDto userDto) throws SQLIntegrityConstraintViolationException;
}
