package com.testmasterapi.api;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

public interface AnswerTemplateApi {
    String BASE_PATH = AnswerApi.BASE_PATH + "-templates";
    String PATH = "/api" + BASE_PATH;
}
