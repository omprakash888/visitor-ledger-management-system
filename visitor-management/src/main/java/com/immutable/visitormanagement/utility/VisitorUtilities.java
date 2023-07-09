package com.immutable.visitormanagement.utility;

import com.immutable.visitormanagement.dto.VisitorDto;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import java.util.Base64;

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

    public void sendReportsInEmail(String filepath,String[] cc,String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("Visitor Summary Report");
            mimeMessageHelper.setFrom("cherrie.cr7@gmail.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setCc(cc);

            FileSystemResource fileSystemResource = new FileSystemResource(new File(filepath));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()),fileSystemResource);
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<ByteArrayResource> downloadExcel(List<VisitorDto> visitors) throws IOException {
         // Replace with your own list of Visitor objects

        // Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Visitors");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Visitor-Id","Visitor Name","Age","gender","contact Number","email","date","check-In-Time","check-out-Time","Type of Visit","Organization Name","Whom To Meet","Reason For Meeting"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Create data rows
        int rowNum = 1;
        for (VisitorDto visitor : visitors) {
            Row row = sheet.createRow(rowNum++);
            // Set cell values for each column
            row.createCell(0).setCellValue(visitor.getVisitorId());
            row.createCell(1).setCellValue(visitor.getVisitorName());
            row.createCell(2).setCellValue(visitor.getAge());
            row.createCell(3).setCellValue(visitor.getGender());
            row.createCell(4).setCellValue(visitor.getContactNumber());
            row.createCell(5).setCellValue(visitor.getEmail());
            row.createCell(6).setCellValue(visitor.getDate());
            row.createCell(7).setCellValue(visitor.getInTime().toString());
            row.createCell(8).setCellValue(visitor.getOutTime().toString());
            row.createCell(9).setCellValue(visitor.getTypeOfVisit());
            row.createCell(10).setCellValue(visitor.getVisitorOrganization());
            row.createCell(11).setCellValue(visitor.getWhomToMeet());
            row.createCell(12).setCellValue(visitor.getReasonForMeeting());
        }

        // Write workbook to output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        // Prepare response with Excel content
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
        byte[] excelBytes = outputStream.toByteArray();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.setContentDisposition(ContentDisposition.attachment().filename("visitors.xlsx").build());

//        HttpHeaders header = new HttpHeaders();
//        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=visitors.xlsx");
//
//        return ResponseEntity
//                .ok()
//                .headers(header)
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);

        return ResponseEntity.ok().headers(header).body(new ByteArrayResource(excelBytes));

    }



}
