package com.testmaster.service.TestService;

import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.TestResultDetailLevel;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.testSession.data.TestSessionResultData;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;

/**
 * Интерфейс для работы с тестами.
 *
 */
public interface TestService {

    /**
     * Метод для получения всех тестов.
     *
     * @param showOnlyDeleted Если <b>true</b> в результате показываются только удаленные тесты
     * @param status статус теста
     * @return Страница списка всех тестов
     */
    @NotNull
    PageData<TestData> getAll(Boolean showOnlyDeleted, TestStatus status, @NotNull Pageable pageable);

    /**
     * Метод для получения всех результатов теста.
     *
     * @param detailLevel фильтрация по статусу теста
     * @return Страница списка всех результатов теста
     */
    @NotNull
    PageData<TestSessionResultData> getResults(Long testId, TestResultDetailLevel detailLevel, @NotNull Pageable pageable);

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

    /**
     * Удалить тест.
     *
     * @param testId Идентификатор теста
     */
    void delete(Long testId);
}
