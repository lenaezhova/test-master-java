package com.testmasterapi.api.GroupApi;

import com.testmasterapi.domain.group.data.GroupData;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Группы", description = "API для работы c группами")
public interface GroupApi {
    String PATH = "/api/groups";

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение списка групп")
    List<GroupData> all();

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить группу",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Группа с таким идентификатором не найдена",
                            content = @Content()
                    )
            }
    )
    GroupData one(@PathVariable Long id);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping
    @Operation(
            summary = "Создать группу",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Группа создана"),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании группы")
            }
    )
    ResponseEntity<Void> create(@RequestBody GroupCreateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновить информацию о группе",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о группе обновлена"),
                    @ApiResponse(responseCode = "404", description = "Группа с таким идентификатором не найдена")
            }
    )
    void update(@PathVariable Long id, @RequestBody GroupUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить группу",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Группа удалена"),
                    @ApiResponse(responseCode = "404", description = "Группа с таким идентификатором не найдена")
            }
    )
    void delete(@PathVariable Long id);
}
