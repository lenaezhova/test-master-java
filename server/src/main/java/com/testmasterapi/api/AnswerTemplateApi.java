package com.testmasterapi.api;

import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Шаблоны ответов")
public interface AnswerTemplateApi {
    String BASE_PATH = AnswerApi.BASE_PATH + "-templates";
    String PATH = "/api" + BASE_PATH;

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{answerTemplateId}")
    @Operation(
            summary = "Обновить информацию в шаблоне ответа",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация в шаблоне ответа обновлена"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Шаблон ответа с таким идентификатором не найден", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения", content = @Content()),
            }
    )
    void update(@PathVariable Long answerTemplateId, @RequestBody AnswerTemplateUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{answerTemplateId}")
    @Operation(
            summary = "Удалить шаблон ответа",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Шаблон овтета удален"),
                    @ApiResponse(responseCode = "403", description = "Вы не являетесь владельцем теста", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Шаблон ответа с таким идентификатором не найден", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Тест открыт для прохождения", content = @Content()),
            }
    )
    void delete(@PathVariable Long answerTemplateId);
}
