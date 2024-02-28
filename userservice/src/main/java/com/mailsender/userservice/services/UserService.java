package com.mailsender.userservice.services;

import com.mailsender.userservice.data.models.User;
import jakarta.mail.MessagingException;

public interface UserService {
    User saveUser(User user) throws MessagingException;
    Boolean verifyToken(String token);
}
