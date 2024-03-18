package com.otusone.Backend.service.emailUtility;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailHandler {
    private static final String EMAIL_USERNAME ="Dailyliveindiapvtltd@gmail.com";
    private static final String EMAIL_PASSWORD ="lyvi mnih qjjk ttwk";
    public static void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("OTP sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    public static void sendPdf(String toEmail, String subject, File file) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.attachFile(file.getAbsolutePath());
            MimeMultipart multipart = new MimeMultipart(mimeBodyPart);
            message.setContent(multipart);
            //message.setText(body);
            Transport.send(message);
            System.out.println("OTP sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
