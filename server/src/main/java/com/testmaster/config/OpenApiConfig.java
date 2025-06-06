package com.testmaster.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Test Master API",
                version = "1.0",
                description = "Документация API"
        ),
        security = @SecurityRequirement(name = "BearerAuth"),
        tags = {
                @Tag(name = "Почта", description = "API для работы c почтой"),

                @Tag(name = "Авторизация", description = "API для работы c авторизацией"),
                @Tag(name = "Пользователи", description = "API для работы c пользователями"),
                @Tag(name = "Группы пользователя", description = "API для работы c группами пользователя"),

                @Tag(name = "Тесты", description = "API для работы c тестами"),
                @Tag(name = "Тесты группы", description = "API для работы c группапи теста"),
                @Tag(name = "Вопросы теста", description = "API для работы c вопросами теста"),
                @Tag(name = "Сессии теста", description = "API для работы c сессиями теста"),

                @Tag(name = "Сессии тестов", description = "API для работы c сессиями тестов"),

                @Tag(name = "Группы", description = "API для работы c группами"),

                @Tag(name = "Вопросы", description = "API для работы c вопросами"),
                @Tag(name = "Шаблоны ответов вопросов", description = "API для работы c шаблонами ответов вопросов"),
                @Tag(name = "Ответы на вопрос", description = "API для работы c ответами на вопрос"),

                @Tag(name = "Ответы", description = "API для работы c ответами"),
        }
)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}