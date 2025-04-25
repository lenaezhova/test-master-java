package com.testmasterapi.domain.user.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTestSessionData {
    private Long id;
    private String name;
    private String email;
}
