package com.testmaster.service.UserService.UserGroupService;

import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.UserGroupsAddRequest;
import com.testmasterapi.domain.page.data.PageData;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Интерфейс для работы с группами пользователя.
 *
 */
public interface UserGroupService {

    /**
     * Метод для получения всех групп пользователя.
     *
     * @return Все группы пользователя
     */
    @NotNull
    PageData<GroupsUserData> getAllGroups(Long userId, @NotNull Pageable pageable);

    /**
     * Метод для добавления пользователя в группу
     *
     * @param userId Идентификатор пользователя
     * @param groupId Идентификатор группы
     * @param request запрос
     */
    void addGroup(Long userId, Long groupId, UserGroupsAddRequest request);

    /**
     * Метод для удаления пользователя из группы.
     *
     * @param userId Идентификатор пользователя
     * @param groupId Идентификатор группы
     */
    void deleteGroup(Long userId, Long groupId);
}
