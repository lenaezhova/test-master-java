package com.testmaster.service.GroupTestService;

import com.testmasterapi.domain.group.request.GroupTestsAddRequest;
import com.testmasterapi.domain.test.data.TestGroupsData;

import java.util.List;

/**
 * Интерфейс для работы с группами тестов.
 *
 */
public interface GroupTestService {
    /**
     * Метод для получения всех тестов группы.
     *
     * @return Все тесты группы
     */
    List<TestGroupsData> getAllTestsGroup(Long groupId);

    /**
     * Метод для добавления теста в группу
     *
     * @param groupId Идентификатор группы
     * @param request запрос
     */
    void add(Long groupId, GroupTestsAddRequest request);

    /**
     * Метод для удаления теста из группы.
     *
     * @param groupId Идентификатор группы
     * @param testId Идентификатор теста
     */
    void delete(Long groupId, Long testId);
}

