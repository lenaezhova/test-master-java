package com.testmasterapi.api;

import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answer.request.AnswerCreateRequest;
import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ответы")
public interface AnswerApi {
    String BASE_PATH = "/answers";
    String PATH = "/api" + BASE_PATH;

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить ответ",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация в ответе обновлена"),
                    @ApiResponse(responseCode = "404", description = "Шаблон ответа с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void update(@PathVariable Long id, @RequestBody AnswerUpdateRequest request);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить ответ",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ответ удален"),
                    @ApiResponse(responseCode = "404", description = "Ответ с таким идентификатором не найден"),
                    @ApiResponse(responseCode = "409", description = "Тест закрыт для прохождения"),
            }
    )
    void delete(@PathVariable Long id);
}
