package com.testmaster.service.GroupUserService;

import com.testmasterapi.domain.group.data.GroupsUserData;
import com.testmasterapi.domain.group.request.GroupUsersAddRequest;
import com.testmasterapi.domain.user.data.UserGroupsData;

import java.util.List;

/**
 * Интерфейс для работы с группами пользователя.
 *
 */
public interface GroupUserService {
    /**
     * Метод для получения всех пользователей группы.
     *
     * @return Все пользователи группы
     */
    List<UserGroupsData> getAllUsersGroup(Long groupId);

    /**
     * Метод для получения всех групп пользователя.
     *
     * @return Все группы пользователя
     */
    List<GroupsUserData> getAllGroupsUser(Long userId);

    /**
     * Метод для добавления пользователя в группу
     *
     * @param groupId Идентификатор группы
     * @param request запрос
     */
    void add(Long groupId, GroupUsersAddRequest request);

    /**
     * Метод для удаления пользователя из группы.
     *
     * @param groupId Идентификатор группы
     * @param userId Идентификатор пользователя
     */
    void delete(Long groupId, Long userId);
}
