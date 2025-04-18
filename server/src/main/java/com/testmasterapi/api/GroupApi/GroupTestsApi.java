package com.testmasterapi.api.GroupApi;

import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.request.UserGroupsAddRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Тесты группы", description = "API для работы c тестами в группе")
@RequestMapping(GroupTestsApi.PATH)
public interface GroupTestsApi {
    String PATH = GroupApi.PATH + "/{groupId}/tests";

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение списка всех тестов группы")
    List<UserGroupsData> all(@PathVariable String groupId);

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @Operation(
            summary = "Добавить тест в группу",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест добавлен в группу"),
                    @ApiResponse(responseCode = "404", description = "Тест (группа) с таким идентификатором не найдены"),
                    @ApiResponse(responseCode = "409", description = "У группы уже есть тест с указанным идентификатором")
            }
    )
    ResponseEntity<Void> add(@PathVariable String groupId, @RequestBody Long testId);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить тест из группы",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест удален из группы"),
                    @ApiResponse(responseCode = "404", description = "Тест (группа) с таким идентификатором не найдены")
            }
    )
    void delete(@PathVariable String groupId, @RequestBody Long testId);
}
