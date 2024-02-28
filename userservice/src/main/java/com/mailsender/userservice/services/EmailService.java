package com.mailsender.userservice.services;

import jakarta.mail.MessagingException;

public interface EmailService  {
    void sendSimpleMailMessage(String name, String to,String token);
    void sendSimpleMailWithAttachments(String name, String to,String token) throws MessagingException;
    void sendSimpleMailWithEmbeddedImage(String name, String to,String token) throws MessagingException;

    void sendHtmlMail(String name, String to,String token);
    void sendHtmlMailEmbeddedFile(String name, String to,String token);

}
