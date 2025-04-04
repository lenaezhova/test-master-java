package com.testmaster.config.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@ConfigurationProperties("auth.local")
@Validated
public record AuthProperties(
        @NotBlank
        String jwtAccessSecret,
        @NotBlank
        String jwtRefreshSecret,
        Duration jwtAccessLifeDuration,
        Duration jwtRefreshLifeDuration
) {
}
