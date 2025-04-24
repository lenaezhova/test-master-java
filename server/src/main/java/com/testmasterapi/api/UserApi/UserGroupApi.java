package com.testmasterapi.api.UserApi;

import com.testmasterapi.api.GroupApi.GroupApi;
import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.UserGroupsAddRequest;
import com.testmasterapi.domain.group.response.GroupsUsersResponse;
import com.testmasterapi.domain.page.data.PageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    PageData<GroupsUserData> allGroups(
            @PathVariable Long userId,

            @ParameterObject
            @PageableDefault(page = 0, size = 10)
            Pageable pageable
    );

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{groupId}")
    @Operation(
            summary = "Добавить пользователя в группу",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь добавлен в группу"),
                    @ApiResponse(responseCode = "404", description = "Группа (пользователь) с таким идентификатором не найдена", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "Пользователь уже состоит в группе с таким идентификатором", content = @Content())
            }
    )
    ResponseEntity<Void> addGroup(
            @PathVariable Long userId,
            @PathVariable Long groupId,
            @RequestBody UserGroupsAddRequest request
    );

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{groupId}")
    @Operation(
            summary = "Удалить пользователя из группы",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователен удален из группы"),
                    @ApiResponse(responseCode = "404", description = "Группа (пользователь) с таким идентификатором не найдена", content = @Content())
            }
    )
    void deleteGroup(
            @PathVariable Long userId,
            @PathVariable Long groupId
    );
}
