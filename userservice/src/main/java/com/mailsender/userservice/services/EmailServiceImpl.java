package com.mailsender.userservice.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmailServiceImpl implements EmailService{
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";

    public static final String UTF_8_ENCODING = "UTF_8_ENCODING";
    @Value("${spring.mail.verify.host}")
    private  String host;

    @Value("${spring.mail.username}")
    private  String fromEmail;


    private final JavaMailSender emailSender;




    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setText("Hey, it's working!");
        emailSender.send(message);

    }

    @Override
    @Async
    public void sendSimpleMailWithAttachments(String name, String to, String token) throws MessagingException {
        MimeMessage message = getMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true, UTF_8_ENCODING);
        helper.setPriority(1);
        helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
        helper.setFrom(fromEmail);
        helper.setTo(to);
//        helper.setText(getEmailMe());
        emailSender.send(message);


    }

    @Override
    @Async
    public void sendSimpleMailWithEmbeddedImage(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendSimpleMailEmbeddedFile(String name, String to, String token) {

    }

    @Override
    public void sendHtmlMail(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendHtmlMailEmbeddedFile(String name, String to, String token) {

    }
    private MimeMessage getMimeMessage(){
        return emailSender.createMimeMessage();
    }
}
