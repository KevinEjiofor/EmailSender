package com.mailsender.userservice.data.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@Entity
@Table(name = "users")

public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   private String name;
   private String email;
   private String password;
   private boolean isEnabled;


}
