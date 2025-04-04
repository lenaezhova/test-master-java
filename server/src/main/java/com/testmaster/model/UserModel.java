package com.testmaster.model;

import api.domain.user.UserRoles;
import api.domain.user.converter.RoleListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel extends BaseEntity {
    private Boolean deleted = false;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "activation_link", nullable = false)
    private String activationLink;

    @Column(name = "is_activate", nullable = false)
    private Boolean isActivate = false;

    @Convert(converter = RoleListConverter.class)
    @Column(columnDefinition = "text[]")
    @Enumerated(EnumType.STRING)
    private List<UserRoles> roles;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;
}