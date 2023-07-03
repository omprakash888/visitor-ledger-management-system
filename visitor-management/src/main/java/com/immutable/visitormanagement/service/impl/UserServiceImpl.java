package com.immutable.visitormanagement.service.impl;

import com.immutable.visitormanagement.dto.UserDto;
import com.immutable.visitormanagement.entity.ConfirmationToken;
import com.immutable.visitormanagement.entity.User;
import com.immutable.visitormanagement.repository.ConfirmationTokenRepository;
import com.immutable.visitormanagement.repository.UserRepository;
import com.immutable.visitormanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void createAdmin(UserDto userDto) {
        User user = mapToUser(userDto);
        this.userRepository.save(user);
        ConfirmationToken confirmationToken1 = new ConfirmationToken(user);
        this.confirmationTokenRepository.save(confirmationToken1);

    }

    private User mapToUser(UserDto userDto) {
        return this.modelMapper.map(userDto,User.class);
    }


}
