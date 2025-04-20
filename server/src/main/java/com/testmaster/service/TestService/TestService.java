package com.testmaster.service.TestService;

import com.testmasterapi.domain.group.data.GroupData;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Интерфейс для работы с тестами.
 *
 */
public interface TestService {

    /**
     * Метод для получения всех тестов.
     *
     * @return Все тесты
     */
    List<TestData> getAll();
    /**
     * Метод для получения одного теста.
     *
     * @param testId Идентификатор теста
     * @return Один тест
     */
    TestData getOne(Long testId);
    /**
     * Метод для создания нового теста.
     *
     * @param request Запрос
     * @return Созданная теста
     */
    @NotNull
    TestData create(@NotNull TestCreateRequest request);

    /**
     * Обновить тест.
     *
     * @param testId Идентификатор теста
     * @param request Запрос
     */
    void update(Long testId, TestUpdateRequest request);
}
