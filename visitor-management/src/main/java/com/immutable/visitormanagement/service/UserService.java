package com.immutable.visitormanagement.service;

import com.immutable.visitormanagement.dto.UserDto;

import java.util.List;

public interface UserService {

    public void createUser();
    public UserDto getUserById(long userId);

    public List<UserDto> getAllUserById();

    public void update();
}
