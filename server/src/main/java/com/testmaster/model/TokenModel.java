package com.testmaster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
}