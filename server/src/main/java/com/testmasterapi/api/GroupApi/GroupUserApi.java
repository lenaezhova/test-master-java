package com.testmasterapi.api.GroupApi;

import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.GroupUsersAddRequest;
import com.testmasterapi.domain.user.data.UserGroupsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Группы пользователей", description = "API для работы c группами пользователей")
public interface GroupUserApi {
    String PATH = GroupApi.PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{groupId}/users")
    @Operation(summary = "Получение списка всех пользователей группы")
    List<UserGroupsData> allUsersGroup(@PathVariable("groupId") Long groupId);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users/{userId}")
    @Operation(summary = "Получение списка всех групп пользователя")
    List<GroupsUserData> allGroupsUser(@PathVariable("userId") Long userId);

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{groupId}/users")
    @Operation(
            summary = "Добавить пользователя в группу",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь добавлен в группу"),
                    @ApiResponse(responseCode = "404", description = "Группа (пользователь) с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "409", description = "У пользователя уже есть группа с указанным идентификатором")
            }
    )
    ResponseEntity<Void> add(@PathVariable("groupId") Long groupId, @RequestBody GroupUsersAddRequest request);

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{groupId}/users/{userId}")
    @Operation(
            summary = "Удалить пользователя из группы",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователен удален из группы"),
                    @ApiResponse(responseCode = "404", description = "Группа (пользователь) с таким идентификатором не найдена")
            }
    )
    void delete(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId);
}
