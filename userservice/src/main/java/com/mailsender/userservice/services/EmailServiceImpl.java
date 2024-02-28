package com.mailsender.userservice.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import static com.mailsender.userservice.utils.EmailUtils.getEmailMessage;
import static com.mailsender.userservice.utils.EmailUtils.getVerificationUrl;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";

    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "email_Template";
    @Value("${spring.mail.verify.host}")
    private  String host;

    @Value("${spring.mail.username}")
    private  String fromEmail;

    private final  TemplateEngine templateEngine;


    private final JavaMailSender emailSender;




    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(getEmailMessage(name, host, token));
            emailSender.send(message);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    @Async
    public void sendSimpleMailWithAttachments(String name, String to, String token) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);

            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(getEmailMessage(name, host, token));

            FileSystemResource image = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image/image-23.png"));
            FileSystemResource picture = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image/photo-23.jpg"));
            FileSystemResource document = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image/project.odt"));

            helper.addAttachment(Objects.requireNonNull(image.getFilename()), image);
            helper.addAttachment(Objects.requireNonNull(picture.getFilename()), picture);
            helper.addAttachment(Objects.requireNonNull(document.getFilename()), document);

            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send mail with attachments: " + e.getMessage(), e);
        }
    }

    @Override
    @Async
    public void sendSimpleMailWithEmbeddedImage(String name, String to, String token){
        try{
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);

            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(getEmailMessage(name, host, token));

            FileSystemResource image = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image/image-23.png"));
            FileSystemResource picture = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image/photo-23.jpg"));
            FileSystemResource document = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/image/project.odt"));



            helper.addInline(getContentId(image.getFilename()), image);
            helper.addInline(getContentId(picture.getFilename()), picture);
            helper.addInline(getContentId(document.getFilename()), document);

            emailSender.send(message);
        }catch(MessagingException e){
            throw new RuntimeException("Failed to send mail with attachments: " + e.getMessage() );
        }

    }


    @Override
    public void sendHtmlMail(String name, String to, String token) {
        try{
            Context context = new Context();
//            context.setVariable("name", name);
//            context.setVariable("url",getVerificationUrl(host,token));
            context.setVariables(Map.of("name", name,"url",getVerificationUrl(host,token)));
            String text = templateEngine.process(EMAIL_TEMPLATE,context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);

            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);


            emailSender.send(message);
        }catch(MessagingException e){
            throw new RuntimeException("Failed to send mail with attachments: " + e.getMessage() );
        }

    }

    @Override
    @Async
    public void sendHtmlMailEmbeddedFile(String name, String to, String token) {

    }
    private MimeMessage getMimeMessage(){
        return emailSender.createMimeMessage();
    }

    private String getContentId(String filename){
        return "<" + filename + ">" ;
    }
}
