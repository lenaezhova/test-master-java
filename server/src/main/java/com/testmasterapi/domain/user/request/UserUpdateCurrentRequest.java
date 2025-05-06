package com.testmasterapi.domain.user.request;

import lombok.Data;

@Data
public class UserUpdateCurrentRequest {
    private String name;
    private String password;
    private Boolean deleted;
}

