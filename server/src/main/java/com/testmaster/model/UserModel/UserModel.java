package com.testmaster.model.UserModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean deleted;
    private String name;
    private String email;
    private String password;
    private String activation_link;
    private Boolean is_activate;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}