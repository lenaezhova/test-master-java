package com.testmaster.service.UserService.UserGroupService;

import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.UserGroupsAddRequest;

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
    List<GroupsUserData> getAllGroups(Long userId);

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
