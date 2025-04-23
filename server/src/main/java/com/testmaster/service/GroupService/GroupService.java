package com.testmaster.service.GroupService;

import com.testmasterapi.domain.group.data.GroupData;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import com.testmasterapi.domain.test.data.TestGroupsData;
import com.testmasterapi.domain.user.data.UserGroupsData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с группами.
 *
 */
public interface GroupService {

    /**
     * Метод для получения всех групп.
     *
     * @return Все группы
     */
    List<GroupData> getAll();

    /**
     * Метод для получения всех пользователей группы.
     *
     * @return Все пользователи группы
     */
    List<UserGroupsData> getAllUsers(Long groupId);

    /**
     * Метод для получения всех тестов группы.
     *
     * @return Все тесты группы
     */
    List<TestGroupsData> getAllTests(Long groupId);

    /**
     * Метод для получения одной группы.
     *
     * @param groupId Идентификатор группы
     * @return Одна группа
     */
    GroupData getOne(Long groupId);

    /**
     * Метод для создания новой группы.
     *
     * @param request Запрос
     * @return Созданная группа
     */
    @NotNull
    GroupData create(@NotNull GroupCreateRequest request);

    /**
     * Метод для удаления группы.
     *
     * @param groupId Идентификатор группы
     */
    void delete(Long groupId);

    /**
     * Обновить группу.
     *
     * @param groupId Идентификатор группы
     * @param request Запрос
     */
    void update(Long groupId, GroupUpdateRequest request);
}