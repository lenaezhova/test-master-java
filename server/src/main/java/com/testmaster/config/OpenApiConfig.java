package com.testmaster.config;

import io.swagger.v3.oas.annotations.info.Info;
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
        tags = {
                @Tag(name = "Вопросы", description = "API для работы c вопросами"),
                @Tag(name = "Шаблоны ответов вопроса", description = "API для работы c шаблонами ответов вопроса"),
                @Tag(name = "Ответы на вопрос", description = "API для работы c ответами на вопрос"),

                @Tag(name = "Пользователи", description = "API для работы c пользователями"),
                @Tag(name = "Группы пользователя", description = "API для работы c группами пользователя"),

                @Tag(name = "Почта", description = "API для работы c почтой"),

                @Tag(name = "Тесты", description = "API для работы c тестами"),
                @Tag(name = "Группы теста", description = "API для работы c группапи теста"),
                @Tag(name = "Вопросы теста", description = "API для работы c вопросами теста"),
                @Tag(name = "Сессии теста", description = "API для работы c сессиями теста"),
                @Tag(name = "Сессии тестов", description = "API для работы c сессиями тестов"),

                @Tag(name = "Группы", description = "API для работы c группами"),

                @Tag(name = "Ответы", description = "API для работы c ответами"),
                @Tag(name = "Шаблоны ответов", description = "API для работы c шаблонами ответов")
        }
)
public class OpenApiConfig {
}