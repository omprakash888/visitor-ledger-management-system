package com.immutable.visitormanagement.service.impl;

import com.immutable.visitormanagement.dto.UserDto;
import com.immutable.visitormanagement.entity.ConfirmationToken;
import com.immutable.visitormanagement.entity.User;
import com.immutable.visitormanagement.repository.ConfirmationTokenRepository;
import com.immutable.visitormanagement.repository.UserRepository;
import com.immutable.visitormanagement.service.UserService;
import com.immutable.visitormanagement.utility.VisitorUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private VisitorUtilities visitorUtilities;

    @Override
    public void signUp(UserDto userDto) throws SQLIntegrityConstraintViolationException {
        User user = mapToUser(userDto);
        try {
            this.userRepository.save(user);
        }
        catch (Exception e) {
            throw new SQLIntegrityConstraintViolationException();
        }

        ConfirmationToken confirmationToken1 = new ConfirmationToken(user);
        this.confirmationTokenRepository.save(confirmationToken1);
        visitorUtilities.sendActivationEmail("http://localhost/8080/api/auth/activateAccount/" + confirmationToken1.getConfirmationToken());
    }

    private User mapToUser(UserDto userDto) {
        return this.modelMapper.map(userDto,User.class);
    }


}
