package com.immutable.visitormanagement.service.impl;

import com.immutable.visitormanagement.dto.UserDto;
import com.immutable.visitormanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void createUser() {

    }

    @Override
    public UserDto getUserById(long userId) {
        return null;
    }

    @Override
    public List<UserDto> getAllUserById() {
        return null;
    }

    @Override
    public void update() {

    }
}
