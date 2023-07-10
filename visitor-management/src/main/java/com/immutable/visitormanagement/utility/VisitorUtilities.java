package com.immutable.visitormanagement.utility;

import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.response.DownloadResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.immutable.visitormanagement.constants.Constants.ADMIN_EMAIL;

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
            context.setVariable("id", visitorDto.getVisitorId());
            context.setVariable("name", visitorDto.getVisitorName());
            context.setVariable("age", visitorDto.getAge());
            context.setVariable("email", visitorDto.getEmail());
            context.setVariable("contactNumber", visitorDto.getContactNumber());
            context.setVariable("whomToMeet",visitorDto.getWhomToMeet());
            context.setVariable("reasonForMeeting",visitorDto.getReasonForMeeting());
            context.setVariable("visitorOrganization",visitorDto.getVisitorOrganization());
            context.setVariable("date",visitorDto.getDate());
            context.setVariable("inTime",visitorDto.getInTime());
            context.setVariable("outTime",visitorDto.getOutTime());
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

    @Async
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

    @Async
    public void  sendReportsEmail(List<DownloadResponse> downloadResponses,String[] emails) throws MessagingException {

        try {

            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Visitor Ledger Sheet");
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("VISITOR ID");
            header.createCell(1).setCellValue("VISITOR NAME");
            header.createCell(2).setCellValue("VISITOR AGE");
            header.createCell(3).setCellValue("VISITOR GENDER");
            header.createCell(4).setCellValue("VISITOR EMAIL");
            header.createCell(5).setCellValue("VISITOR CONTACT NUMBER");
            header.createCell(6).setCellValue("VISITOR TYPE OF VISIT");
            header.createCell(7).setCellValue("VISITOR ORGANIZATION");
            header.createCell(8).setCellValue("VISITOR WHOM TO MEET");
            header.createCell(9).setCellValue(" DATE");
            header.createCell(10).setCellValue("VISITOR CHECK IN_TIME");
            header.createCell(11).setCellValue("VISITOR CHECK OUT_TIME");
            header.createCell(12).setCellValue("REASON FOR MEETING");

            int rowIndex = 1;
            for(DownloadResponse downloadResponse : downloadResponses) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(downloadResponse.getVisitorId());
                dataRow.createCell(1).setCellValue(downloadResponse.getVisitorName());
                dataRow.createCell(2).setCellValue(downloadResponse.getAge());
                dataRow.createCell(3).setCellValue(downloadResponse.getGender());
                dataRow.createCell(4).setCellValue(downloadResponse.getEmail());
                dataRow.createCell(5).setCellValue(downloadResponse.getContactNumber());
                dataRow.createCell(6).setCellValue(downloadResponse.getTypeOfVisit());
                dataRow.createCell(7).setCellValue(downloadResponse.getVisitorOrganization());
                dataRow.createCell(8).setCellValue(downloadResponse.getWhomToMeet());
                dataRow.createCell(9).setCellValue(downloadResponse.getDate());
                dataRow.createCell(10).setCellValue(downloadResponse.getInTime());
                dataRow.createCell(11).setCellValue(downloadResponse.getOutTime());
                dataRow.createCell(12).setCellValue(downloadResponse.getReasonForMeeting());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            byte[] excelBytes = outputStream.toByteArray();
            ByteArrayResource resource = new ByteArrayResource(excelBytes) {
                public String getFilename() {
                    String fileName = "visitor_ledger_sheet " + LocalDate.now() + LocalTime.now() +".xls";
                    return fileName;
                }
            };
            workbook.close();
            outputStream.close();

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setSubject("Visitor Summary Report");
            helper.setFrom(ADMIN_EMAIL);
            helper.setText("please find attachment below");
            helper.setTo(emails);
            helper.addAttachment(resource.getFilename(), resource);

            mailSender.send(mimeMessage);
        }
        catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }


}
