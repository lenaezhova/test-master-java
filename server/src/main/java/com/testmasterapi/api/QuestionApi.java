package com.testmasterapi.api;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Вопросы", description = "API для работы c вопросами")
public interface QuestionApi {
    String PATH = "/api/questions";
}
