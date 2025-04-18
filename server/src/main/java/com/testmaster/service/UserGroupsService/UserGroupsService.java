package com.testmaster.service.UserGroupsService;

import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.request.UserGroupsAddRequest;

import java.util.List;

/**
 * Интерфейс для работы с группами пользователя.
 *
 */
public interface UserGroupsService {
    /**
     * Метод для получения всех групп пользователя.
     *
     * @return Все группы пользователя
     */
    List<UserGroupsData> getAll();

    /**
     * Метод для добавления группы пользователю.
     *
     * @param request Запрос
     */
    UserGroupsData add(Long groupId);

    /**
     * Метод для удаление группы у пользователю.
     *
     * @param groupId Идентификатор группы
     */
    void delete(Long groupId);
}
