package com.testmasterapi.domain.user.request;

import com.testmasterapi.domain.user.UserRoles;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateRequest extends UserUpdateCurrentRequest {
    private Set<UserRoles> roles;
    private Boolean isActivate;
    private String activationLink;
}

