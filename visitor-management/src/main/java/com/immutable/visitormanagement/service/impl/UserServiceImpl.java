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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;



@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final VisitorUtilities visitorUtilities;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository, PasswordEncoder passwordEncoder, VisitorUtilities visitorUtilities) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.visitorUtilities = visitorUtilities;
    }

    @Override
    public void signUp(UserDto userDto) throws SQLIntegrityConstraintViolationException {
        User user = mapToUser(userDto);
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
        }
        catch (Exception e) {
            throw new SQLIntegrityConstraintViolationException();
        }

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        this.confirmationTokenRepository.save(confirmationToken);
        visitorUtilities.sendActivationEmail("http://localhost:8080/api/auth/activateAccount/" + confirmationToken.getConfirmationToken());
    }

    @Override
    public String forgotPassword(String email) {
        User user = this.userRepository.findByEmailIgnoreCase(email);
        if(user == null) {
            return "You haven't registered with these email, Please Go and register";
        }
        if(!user.isEnabled()) {
            return "your account is not activated, please wait for your account activation";
        }
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        this.confirmationTokenRepository.save(confirmationToken);
        this.visitorUtilities.sendResetPasswordLink(user.getEmail(),"http://localhost:8080/api/auth/reset-password/" + confirmationToken.getConfirmationToken());
        return "reset-password link sent to your email Go and Check";
    }

    @Override
    public String resetPassword(String token, String password) {
        ConfirmationToken confirmationToken = this.confirmationTokenRepository.findByConfirmationToken(token);

        if(confirmationToken == null || confirmationToken.isExpired())  {
            return "your request url is invalid, please use correct url";
        }
        if(!confirmationToken.getUser().isEnabled()) {
            return "Your account hasn't been activated";
        }
        confirmationToken.setExpired(true);
        User user = confirmationToken.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        this.confirmationTokenRepository.save(confirmationToken);

        return "your password was changed successfully, please go and login";

    }
    private User mapToUser(UserDto userDto) {
        return this.modelMapper.map(userDto,User.class);
    }

}
