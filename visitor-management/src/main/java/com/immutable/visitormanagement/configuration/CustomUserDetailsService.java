package com.immutable.visitormanagement.configuration;

import com.immutable.visitormanagement.entity.User;
import com.immutable.visitormanagement.exception.ResourceNotFoundException;
import com.immutable.visitormanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User","Email"+username,0));
        return user;
    }
}
