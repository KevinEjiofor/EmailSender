package com.mailsender.userservice.services;

import com.mailsender.userservice.data.models.User;

public interface UserService {
    User saveUser(User user);
    Boolean verifyToken(String token);
}
