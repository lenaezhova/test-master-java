package com.testmaster.validation;

import api.domain.user.request.CreateUserRequest;
import org.springframework.stereotype.Service;

import static com.testmaster.exeption.ClientException.UnprocessableEntity;
import static org.springframework.util.StringUtils.hasText;

@Service
public class PasswordValidationService {
    private static final String EMPTY_PASSWORD = "Password cannot be empty!";

    public void validate(CreateUserRequest data) {
        if (!hasText(data.password())) {
            throw UnprocessableEntity(EMPTY_PASSWORD);
        }
    }
}


