package com.testmasterapi.api.GroupApi;

import com.testmasterapi.api.TestApi.TestApi;
import com.testmasterapi.api.UserApi.UserApi;
import com.testmasterapi.domain.group.data.GroupData;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import com.testmasterapi.domain.group.response.GroupsResponse;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.data.TestGroupsData;
import com.testmasterapi.domain.test.response.TestsGroupsResponse;
import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.response.UsersGroupsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Группы")
public interface GroupApi {
    String BASE_PATH = "/groups";
    String PATH = "/api" + BASE_PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение списка групп")
    PageData<GroupData> all(
            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{groupId}" + UserApi.BASE_PATH)
    @Operation(summary = "Получение списка всех пользователей группы")
    PageData<UserGroupsData> allUsers(
            @PathVariable Long groupId,

            @Parameter( description = "Показать удаленных пользователями", example = "false")
            @RequestParam(required = false)
            Boolean showUserDeleted,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{groupId}" + TestApi.BASE_PATH)
    @Operation(summary = "Получение списка всех тестов группы")
    PageData<TestGroupsData> allTests(
            @PathVariable Long groupId,

            @Parameter( description = "Показать удаленные тесты", example = "false")
            @RequestParam(required = false)
            Boolean showTestDeleted,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{groupId}")
    @Operation(
            summary = "Получить группу",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Группа с таким идентификатором не найдена")
            }
    )
    GroupData one(@PathVariable Long groupId);

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
    @PatchMapping("/{groupId}")
    @Operation(
            summary = "Обновить информацию о группе",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Информация о группе обновлена"),
                    @ApiResponse(responseCode = "404", description = "Группа с таким идентификатором не найдена")
            }
    )
    void update(@PathVariable Long groupId, @RequestBody GroupUpdateRequest request);

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @DeleteMapping("/{groupId}")
    @Operation(
            summary = "Удалить группу",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Группа удалена"),
                    @ApiResponse(responseCode = "404", description = "Группа с таким идентификатором не найдена")
            }
    )
    void delete(@PathVariable Long groupId);
}
