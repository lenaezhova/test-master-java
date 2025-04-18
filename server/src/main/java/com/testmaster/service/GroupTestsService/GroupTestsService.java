package com.testmaster.service.GroupTestsService;

import com.testmasterapi.domain.group.request.GroupTestsAddRequest;
import com.testmasterapi.domain.user.data.UserGroupsData;
import com.testmasterapi.domain.user.request.UserGroupsAddRequest;

import java.util.List;

/**
 * Интерфейс для работы с тестами в группе.
 *
 */
public interface GroupTestsService {
    /**
     * Метод для получения всех тестов в группе.
     * @param groupId Идентификатор группы
     * @return Все группы пользователя
     */
    List<UserGroupsData> getAll(Long groupId);

    /**
     * Метод для добавления теста в группе.
     * @param groupId Идентификатор группы
     * @param testId Идентификатор теста
     * @param request Запрос
     */
    UserGroupsData add(Long groupId, Long testId);

    /**
     * Метод для удаления теста из группа.
     * @param testId Идентификатор теста
     */
    void delete(Long testId);
}

