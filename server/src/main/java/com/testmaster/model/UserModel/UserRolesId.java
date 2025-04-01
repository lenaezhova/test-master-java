package com.testmaster.model.UserModel;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserRolesId implements Serializable {
    private Long userId;
    private Long roleId;
}