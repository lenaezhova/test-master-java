package com.testmaster.model.UserModel;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserGroupsId implements Serializable {
    private Long userId;
    private Long groupId;
}