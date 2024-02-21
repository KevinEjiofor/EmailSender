package com.mailsender.userservice.data.repository;

import com.mailsender.userservice.data.models.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Confirmation findByToken(String token);

}
