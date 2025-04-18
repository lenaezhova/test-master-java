package com.testmasterapi.domain.user.request;

import com.testmasterapi.domain.user.UserRoles;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateRequest {
    private String name;
    private String password;
    private Boolean deleted;
    private Boolean isActivate;
    private String activationLink;
    private List<UserRoles> roles;
}

