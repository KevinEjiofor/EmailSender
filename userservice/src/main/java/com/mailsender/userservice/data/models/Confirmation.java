package com.mailsender.userservice.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
@Entity
@Table(name = "confirmations")


public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String token;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public  Confirmation(User user) {
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }


}
