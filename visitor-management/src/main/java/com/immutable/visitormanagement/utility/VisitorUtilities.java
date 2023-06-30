//package com.immutable.visitormanagement.utility;
//
//public class VisitorUtilities {
//
//    public String parseEmailTemplate(List<Object> stringList, List<Object> afterList, List<Object> beforeObjectList, CounterParty counterParty) {
//        if (stringList != null && !stringList.isEmpty()) {
//            int i = 0;
//            StringBuilder template = new StringBuilder("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n"
//                    + "<title>Page Title</title>\n" + "</head>\n" + "<body style=\"margin:0;padding:0;\">\n"
//                    + "    <table role=\"presentation\"\n"
//                    + "        style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#22252b;\">\n"
//                    + "        <tr>\n" + "            <td align=\"center\" style=\"padding:0;\">\n"
//                    + "                <table role=\"presentation\" bgcolor=\"#2f323\"\n"
//                    + "                    style=\"width:602px;border-collapse:collapse;border-spacing:0;text-align:left;color:white\">\n"
//                    + "\n" + "                    <tr>\n"
//                    + "                        <td style=\"padding: 15px 30px 15px 30px;background:#22252B;\">\n"
//                    + "                            <table\n"
//                    + "                                style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;font-size:9px;font-family:Arial,sans-serif;\">                         <td style=\"padding:0;width:50%;\" align=\"left\">\n"
//                    + "                        <img src=\"https://rms-portal-dev.olamdigital.com/assets/img/Olam_MasterLogo_White.png\" height=\"45\" width=\"125\">\n"
//                    + "                        </td>\n"
//                    + "                        <td style=\"padding:0;width:50%;\" align=\"right\">\n"
//                    + "                            <p style=\"font-size: 20px;color: #00E096;\">Engage Portal</p>\n"
//                    + "                        </td>\n" + "                        </table>\n"
//                    + "                        </td>\n" + "                    </tr>\n" + "                    <tr>\n"
//                    + "                        <td style=\"background: #2F3237D0;\">\n"
//                    + "                            <table role=\"presentation\"\n"
//                    + "                                style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\">\n"
//                    + "                                <tr>\n"
//                    + "                                    <td style=\"padding: 30px;color: white;\">\n"
//                    + "                                        <h1 style=\"font-size:15px;margin:0 0 20px 0;font-family:Arial,sans-serif;\">\n"
//                    + "                                            Hi All,</h1>");
//            template.append("<p style= margin:0 0 12px 0;font-size:15px;line-height:24px;font-family:Arial,sans-serif;color: #afa5a5;>");
//            template.append("The below changes are done by credit team for ");
//            template.append("Counterparty code:(" + counterParty.getCpCode() + ")");
//            template.append(" and ");
//            template.append("Counterparty name:(" + counterParty.getCpName() + ")");
//            template.append("</p> <br>");
//            template.append(
//                    "<table border=\"1\" role=\"presentation\" style=\"width:60%;border-collapse:collapse;border:1px solid #cccccc;border-spacing:0\">\n"
//                            + "                                            <tr style=\"border-color: #96D4D4;\">\n"
//                            + "                                                <td style=\"padding:5px;\">\n"
//                            + "                                                    Field name\n"
//                            + "                                                </td>\n"
//                            + "                                                <td style=\"padding:5px;width:100px\">\n"
//                            + "                                                    Current value\n"
//                            + "                                                </td>\n"
//                            + "                                                <td style=\"padding:5px;width:100px\">\n"
//                            + "                                                    Previous value\n"
//                            + "                                                </td>\n"
//                            + "                                            </tr>\n");
//            int j = 0;
//            for (Object keyName : stringList) {
//                template.append("<tr>");
//                template.append("<td style='padding:5px'>");
//                template.append(keyName);
//                template.append("</td>");
//                template.append("<td style='padding:5px'>");
//                template.append(beforeObjectList.get(j));
//                template.append("</td>");
//                template.append("<td style='padding:5px'>");
//                template.append(afterList.get(j));
//                template.append("</td>");
//                j++;
//            }
//            String l = "</table><p><br><span style= color: #afa5a5; >Regards,</span> <br><br>"
//                    + "<span style=color: white;>Engage Digital Support</span>"
//                    + " </p></td></tr></table></td></tr><tr><td style=padding-top: 20px;text-align: center;color: #FFFFFF80;"
//                    + " &copy; 2021 Olam International All Rights Reserved Co. Reg. No. 199504676H</td></tr></table></td></tr></table>"
//                    + "</body>" + "</html>";
//            template.append(l);
//            return template.toString();
//        }
//        return null;
//    }
//
//    public void sendEmail(EmailDTO mail, String template, CounterParty counterParty) {
//        try {
//            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//            javaMailSender.setHost("smtp.office365.com");
//            javaMailSender.setPort(587);
//            javaMailSender.setUsername("digitalapps@olamnet.com");
//            javaMailSender.setPassword("Secure2021");
//            Properties props = javaMailSender.getJavaMailProperties();
//            props.put("mail.transport.protocol", "smtp");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.debug", "true");
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setSubject("RMS Credit Changes for CounterpartyCode:" + counterParty.getCpCode());
//            mimeMessageHelper.setFrom("digitalapps@olamnet.com");
//            mimeMessageHelper.setTo(mail.getToMail());
//            mimeMessageHelper.setText(mail.getMailContent(), true);
//            mimeMessage.setContent(template, "text/html; charset=utf-8");
//            javaMailSender.send(mimeMessageHelper.getMimeMessage());
//        } catch (Exception e) {
//            logger.error("Internal Server Error");
//            throw new RMSManagementException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//        }
//    }
//}
