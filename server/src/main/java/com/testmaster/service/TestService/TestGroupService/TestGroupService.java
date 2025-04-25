package com.testmaster.service.TestService.TestGroupService;

import com.testmasterapi.domain.group.request.TestsGroupAddRequest;

/**
 * Интерфейс для работы с тестами группы.
 *
 */
public interface TestGroupService {

    /**
     * Метод для добавления теста в группу
     *
     * @param testId Идентификатор теста
     * @param groupId Идентификатор группы
     * @param request запрос
     */
    void addTest(Long testId, Long groupId, TestsGroupAddRequest request);

    /**
     * Метод для удаления теста из группы.
     *
     * @param testId Идентификатор теста
     * @param groupId Идентификатор группы
     */
    void deleteTest(Long testId, Long groupId);
}
