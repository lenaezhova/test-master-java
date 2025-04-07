package com.testmaster.dto;

import api.domain.user.UserRoles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Boolean isActivate;
    private String activationLink;
    private List<UserRoles> roles;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}