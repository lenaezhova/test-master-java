package com.testmasterapi.api.UserApi;

import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.request.UserGroupsAddRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Группы пользователя", description = "API для работы c группами пользователя")
public interface UserGroupsApi {
    String PATH = UserApi.PATH + "/groups";

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение списка всех групп пользователя")
    List<UserGroupsData> all();

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @Operation(
            summary = "Добавить группу пользователю",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Группа добавлена"),
                    @ApiResponse(responseCode = "404", description = "Группа (пользователь) с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "409", description = "У пользователя уже есть группа с указанным идентификатором")
            }
    )
    ResponseEntity<Void> add(@RequestBody Long groupId);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить группу у пользователя",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Группа удалена"),
                    @ApiResponse(responseCode = "404", description = "Группа (пользователь) с таким идентификатором не найдена")
            }
    )
    void delete(@PathVariable("id") Long id);
}
