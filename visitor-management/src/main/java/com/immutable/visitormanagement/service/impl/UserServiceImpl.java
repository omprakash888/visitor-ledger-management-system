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
import java.util.UUID;

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

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        this.confirmationTokenRepository.save(confirmationToken);
        visitorUtilities.sendActivationEmail("http://localhost/8080/api/auth/activateAccount/" + confirmationToken.getConfirmationToken());
    }

    @Override
    public String forgotPassword(String email) {
        User user = this.userRepository.findByEmailIgnoreCase(email);
        if(user == null) {
            return "You haven't registered with these email, Please Go and register";
        }
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        this.confirmationTokenRepository.save(confirmationToken);
        this.visitorUtilities.sendResetPasswordLink(user.getEmail(),"http://localhost/8080/api/auth/reset-password/" + confirmationToken.getConfirmationToken());
        return "reset-password link sent to your email Go and Check";
    }

    @Override
    public String resetPassword(String token, String password) {
        ConfirmationToken confirmationToken = this.confirmationTokenRepository.findByConfirmationToken(token);

        if(confirmationToken == null)  {
            return "your request url is invalid, please use correct url";
        }
        if(confirmationToken.getUser().isEnabled()) {
            return "Your account has been activated";
        }
        User user = confirmationToken.getUser();

        return null;


    }

    private User mapToUser(UserDto userDto) {
        return this.modelMapper.map(userDto,User.class);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
