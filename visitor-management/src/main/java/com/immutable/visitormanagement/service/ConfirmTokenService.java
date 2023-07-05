package com.immutable.visitormanagement.service;

import org.springframework.stereotype.Component;


public interface ConfirmTokenService {
    public String verify(String token);
}
