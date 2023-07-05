package com.immutable.visitormanagement.service.impl;

import com.immutable.visitormanagement.entity.ConfirmationToken;
import com.immutable.visitormanagement.entity.User;
import com.immutable.visitormanagement.repository.ConfirmationTokenRepository;
import com.immutable.visitormanagement.repository.UserRepository;
import com.immutable.visitormanagement.service.ConfirmTokenService;
import com.immutable.visitormanagement.utility.VisitorUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmTokenImpl implements ConfirmTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VisitorUtilities visitorUtilities;
    @Override
    public String verify(String token) {
        ConfirmationToken confirmationToken = this.confirmationTokenRepository.findByConfirmationToken(token);

        if(confirmationToken == null)  {
            return "your request url is invalid, please use correct url";
        }
        if(confirmationToken.getUser().isEnabled()) {
            return "Your request url has been expired";
        }
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        this.userRepository.save(user);
        confirmationToken.setExpired(true);
        this.confirmationTokenRepository.save(confirmationToken);
        this.visitorUtilities.sendMailForAccountConfirmation(user.getName(),user.getEmail());
        return "Thanks For Your Response";
    }
}
