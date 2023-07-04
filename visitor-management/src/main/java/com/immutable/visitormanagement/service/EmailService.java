package com.immutable.visitormanagement.service;

import com.immutable.visitormanagement.entity.EmailDetails;

public interface EmailService {
	String sendSimpleMail(EmailDetails details);
	String sendMailWithAttachment(EmailDetails details);

}
