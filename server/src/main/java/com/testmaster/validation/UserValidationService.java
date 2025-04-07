package com.testmaster.validation;

import com.testmasterapi.domain.user.request.CreateUserRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static com.testmaster.exeption.ClientException.UnprocessableEntity;
import static org.springframework.util.StringUtils.hasText;

@Service
public class UserValidationService {
    private static final List<Pattern> EMAIL_PATTERNS = List.of(
            Pattern.compile("^\\S+@\\w+[.][a-z]+$")
    );

    private static final String EMPTY_EMAIL = "Email cannot be empty!";

    private static final String INVALID_EMAIL = "Invalid email format!";

    public void validate(CreateUserRequest data) {
        if (!hasText(data.email())) {
            throw UnprocessableEntity(EMPTY_EMAIL);
        }

        validateByPatterns(data.email());
    }

    private void validateByPatterns(@Nullable String email) {
        if (notValidNullable(email, EMAIL_PATTERNS)) {
            throw UnprocessableEntity(INVALID_EMAIL);
        }
    }

    private boolean patternsNotMatched(@Nonnull String input, @Nonnull List<Pattern> patterns) {
        return patterns.stream().anyMatch(pattern -> !pattern.matcher(input).find());
    }

    private boolean notValidNullable(@Nullable String input, @Nonnull List<Pattern> patterns) {
        return input != null && patternsNotMatched(input, patterns);
    }
}
