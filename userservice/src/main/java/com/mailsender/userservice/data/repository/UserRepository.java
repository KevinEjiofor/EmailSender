package com.mailsender.userservice.data.repository;

import com.mailsender.userservice.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findUserByEmail(String email);

    boolean existsByEmail(String email);

    User findByEmailIgnoreCase(String email);
}