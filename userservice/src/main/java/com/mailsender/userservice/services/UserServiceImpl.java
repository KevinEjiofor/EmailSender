package com.mailsender.userservice.services;

import com.mailsender.userservice.data.models.Confirmation;
import com.mailsender.userservice.data.models.User;
import com.mailsender.userservice.data.repository.ConfirmationRepository;
import com.mailsender.userservice.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final   EmailService emailService;


    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){throw new RuntimeException("Email already exists");
        }
        user.setEnabled(false);
        userRepository.save(user);

        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);
        emailService.sendSimpleMailMessage(user.getName(),user.getEmail(),confirmation.getToken());

        return user;

    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation newConfirmation = confirmationRepository.findByToken(token);
        User user = userRepository.findByEmailIgnoreCase(newConfirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        confirmationRepository.delete(newConfirmation);

        return Boolean.TRUE;
    }
}
