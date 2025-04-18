package com.testmaster.service.validation;

import com.testmasterapi.domain.user.request.UserCreateRequest;
import org.springframework.stereotype.Service;

import static com.testmaster.exeption.ClientException.UnprocessableEntity;
import static org.springframework.util.StringUtils.hasText;

@Service
public class PasswordValidationService {
    private static final String EMPTY_PASSWORD = "Password cannot be empty!";

    public void validate(UserCreateRequest data) {
        if (!hasText(data.password())) {
            throw UnprocessableEntity(EMPTY_PASSWORD);
        }
    }
}


