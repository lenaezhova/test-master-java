package com.testmaster.service.TestService;

import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
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
     * @return Страница списка всех тестов
     */
    @NotNull
    PageData<TestData> getAll(Boolean showDeleted, @NotNull Pageable pageable);

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
