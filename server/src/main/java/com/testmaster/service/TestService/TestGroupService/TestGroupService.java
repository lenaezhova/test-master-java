package com.testmaster.service.TestService.TestGroupService;

import com.testmasterapi.domain.group.request.TestsGroupAddRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
