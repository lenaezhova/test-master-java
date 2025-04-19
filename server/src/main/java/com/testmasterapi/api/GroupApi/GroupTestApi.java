package com.testmasterapi.api.GroupApi;

import com.testmasterapi.domain.group.request.GroupTestsAddRequest;
import com.testmasterapi.domain.test.data.TestGroupsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Группы тестов", description = "API для работы c группами тестов")
public interface GroupTestApi {
    String PATH = GroupApi.PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{groupId}/tests")
    @Operation(summary = "Получение списка всех тестов группы")
    List<TestGroupsData> allTestsGroup(@PathVariable("groupId") Long groupId);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @PostMapping("/{groupId}/tests")
    @Operation(
            summary = "Добавить тест в группу",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Тест добавлен в группу"),
                    @ApiResponse(responseCode = "404", description = "Группа (тест) с таким идентификатором не найдены"),
                    @ApiResponse(responseCode = "409", description = "В группе уже есть тесты с таким идентификатором")
            }
    )
    ResponseEntity<Void> add(@PathVariable("groupId") Long groupId, @RequestBody GroupTestsAddRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{groupId}/tests/{testId}")
    @Operation(
            summary = "Удалить тест из группы",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователен удален из группы"),
                    @ApiResponse(responseCode = "404", description = "Группа (тест) с таким идентификатором не найдены")
            }
    )
    void delete(@PathVariable("groupId") Long groupId, @PathVariable("testId") Long testId);
}
