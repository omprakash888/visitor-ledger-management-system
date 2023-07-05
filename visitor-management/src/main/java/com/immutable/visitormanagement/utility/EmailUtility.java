package com.immutable.visitormanagement.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailUtility {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(SimpleMailMessage mail) {
        javaMailSender.send(mail);
    }
}
