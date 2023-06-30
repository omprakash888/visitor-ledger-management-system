package com.immutable.visitormanagement.utility;

import com.immutable.visitormanagement.dto.VisitorDto;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
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
            String html = templateEngine.process("visitorEmailTemplate", context);

            mimeMessageHelper.setSubject("Visitor registration Successful");
            mimeMessageHelper.setFrom("cherrie.cr7@gmail.com");
            mimeMessageHelper.setTo(visitorDto.getEmail());
            mimeMessageHelper.setText("Your visitor Id", true);
            mimeMessageHelper.setText(html, true);
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
