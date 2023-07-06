package com.immutable.visitormanagement.utility;

import com.immutable.visitormanagement.dto.VisitorDto;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class VisitorUtilities {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public VisitorUtilities(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(VisitorDto visitorDto) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            // Load HTML email template using Thymeleaf
            Context context = new Context();
            context.setVariable("id", visitorDto.getId());
            context.setVariable("name", visitorDto.getVisitorName());
            context.setVariable("age", visitorDto.getAge());
            context.setVariable("email", visitorDto.getEmail());
            context.setVariable("contactNumber", visitorDto.getContactNumber());
            context.setVariable("photoData",visitorDto.getPhotoPath());
            context.setVariable("whomToMeet",visitorDto.getWhomToMeet());
            context.setVariable("reasonForMeeting",visitorDto.getReasonForMeeting());
            context.setVariable("visitorOrganization",visitorDto.getVisitorOrganization());
            context.setVariable("date",visitorDto.getDate());
            context.setVariable("checkIn",visitorDto.getCheckIn());
            String html = templateEngine.process("visitorEmailTemplate", context);

            mimeMessageHelper.setSubject("Successful Registration - Important Reminder");
            mimeMessageHelper.setFrom("cherrie.cr7@gmail.com");
            mimeMessageHelper.setTo(visitorDto.getEmail());
            mimeMessageHelper.setText("Your visitor Id", true);
            mimeMessageHelper.setText(html, true);
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendActivationEmail(String link) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            // Load HTML email template using Thymeleaf
            Context context = new Context();
            context.setVariable("link", link);
            String html = templateEngine.process("accountActivation", context);

            mimeMessageHelper.setSubject("Successful Registration - Important Reminder");
            mimeMessageHelper.setFrom("cherrie.cr7@gmail.com");
            mimeMessageHelper.setTo("prakash888.kamarthi@gmail.com");
            mimeMessageHelper.setText(html, true);
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendMailForAccountConfirmation(String name, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            // Load HTML email template using Thymeleaf
            Context context = new Context();
            context.setVariable("name", name);
            String html = templateEngine.process("AccountConfirmation", context);

            mimeMessageHelper.setSubject("Your Account Activated");
            mimeMessageHelper.setFrom("cherrie.cr7@gmail.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(html, true);
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendResetPasswordLink(String email, String link) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("Reset-Password");
            mimeMessageHelper.setFrom("cherrie.cr7@gmail.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText("Your Account reset link :- ");
            mimeMessageHelper.setText(link);
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
