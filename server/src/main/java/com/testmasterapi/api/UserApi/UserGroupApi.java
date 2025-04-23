package com.testmasterapi.api.UserApi;

import com.testmasterapi.api.GroupApi.GroupApi;
import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.UserGroupsAddRequest;
import com.testmasterapi.domain.group.response.GroupsUsersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Группы пользователя")
@RequestMapping(value = UserApi.PATH + "/{userId}" + GroupApi.BASE_PATH)
public interface UserGroupApi {
    String PATH = UserApi.PATH + "/{userId}" + GroupApi.BASE_PATH;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Получение списка всех групп пользователя")
    GroupsUsersResponse allGroups(@PathVariable("userId") Long userId);

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{groupId}")
    @Operation(
            summary = "Добавить пользователя в группу",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь добавлен в группу"),
                    @ApiResponse(responseCode = "404", description = "Группа (пользователь) с таким идентификатором не найдена"),
                    @ApiResponse(responseCode = "409", description = "Пользователь уже состоит в группе с таким идентификатором")
            }
    )
    ResponseEntity<Void> addGroup(
            @PathVariable("userId") Long userId,
            @PathVariable("groupId") Long groupId,
            @RequestBody UserGroupsAddRequest request
    );

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{groupId}")
    @Operation(
            summary = "Удалить пользователя из группы",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователен удален из группы"),
                    @ApiResponse(responseCode = "404", description = "Группа (пользователь) с таким идентификатором не найдена")
            }
    )
    void deleteGroup(@PathVariable("userId") Long userId, @PathVariable("groupId") Long groupId);
}
