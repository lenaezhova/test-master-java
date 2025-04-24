package com.testmaster.service.TestService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.TestMapper;
import com.testmaster.model.Question;
import com.testmaster.model.User.User;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmaster.service.TestService.TestQuestionService.DefaultTestQuestionService;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.event.TestDeletedEvent;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import com.testmasterapi.domain.user.data.UserData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.testmaster.model.Test.Test;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.testmaster.repository.TestRepository.TestRepository;

import java.util.List;
import java.util.function.LongSupplier;

@Service
@RequiredArgsConstructor
public class DefaultTestService implements TestService {
    private final TestMapper testMapper;

    private final TestRepository testRepository;
    private final UserRepository userRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final String notFoundTestMessage = "Тест не найден";

    @NotNull
    @Override
    public PageData<TestData> getAll(Boolean showOnlyDeleted, @NotNull Pageable pageable) {
        var content = testRepository.findAllTests(showOnlyDeleted, pageable)
                .stream()
                .map(testMapper::toData)
                .toList();

        LongSupplier total = () -> testRepository.countAllTests(showOnlyDeleted);

        Page<TestData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @Override
    public TestData getOne(Long id) {
        return testMapper.toData(this.getTest(id));
    }

    @NotNull
    @Transactional
    @Override
    public TestData create(@NotNull TestCreateRequest request) {
        var currentUser = getCurrentUser();

        Test test = testMapper.toEntity(request, currentUser);
        testRepository.save(test);

        return testMapper.toData(test);
    }

    @Override
    @Transactional
    public void update(Long id, TestUpdateRequest updateRequest) {
        int updated = testRepository.update(id, updateRequest);
        if (updated == 0) {
            throw new NotFoundException(notFoundTestMessage);
        }
    }

    @Override
    @Transactional
    public void delete(Long testId) {
        applicationEventPublisher.publishEvent(new TestDeletedEvent(testId));

        var updateRequest = new TestUpdateRequest();
        updateRequest.setDeleted(true);
        int updated = testRepository.update(testId, updateRequest);

        if (updated == 0) {
            throw new NotFoundException(notFoundTestMessage);
        }
    }


    private Test getTest(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException(notFoundTestMessage));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private User getCurrentUser() {
        var customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.getUser(customUserDetails.getId());
    }

}
