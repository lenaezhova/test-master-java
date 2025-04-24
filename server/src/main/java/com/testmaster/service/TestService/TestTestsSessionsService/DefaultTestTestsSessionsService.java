package com.testmaster.service.TestService.TestTestsSessionsService;

import com.testmaster.events.TestSessionClosedEventListener;
import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.TestSessionMapper;
import com.testmaster.model.Group;
import com.testmaster.model.Test.Test;
import com.testmaster.model.Test.TestGroup;
import com.testmaster.model.TestSession;
import com.testmaster.model.User.User;
import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmaster.repository.TestGroupRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.group.request.TestsGroupAddRequest;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.test.TestGroupId;
import com.testmasterapi.domain.test.event.TestDeletedEvent;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.testSession.TestSessionStatus;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.event.TestSessionClosedEvent;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import com.testmasterapi.domain.user.data.UserData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.LongSupplier;

@Service
@RequiredArgsConstructor
public class DefaultTestTestsSessionsService implements TestTestsSessionsService {
    private final TestSessionMapper testSessionMapper;

    private final TestSessionRepository testSessionRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @NotNull
    @Override
    public PageData<TestSessionData> getAllSessions(Long testId, Boolean showUserDeleted, @NotNull Pageable pageable) {
        var content = testSessionRepository.findAllByTestId(testId, showUserDeleted, pageable)
                .stream()
                .map(testSessionMapper::toData)
                .toList();

        LongSupplier total = () -> testSessionRepository.countAllByTestId(testId, showUserDeleted);

        Page<TestSessionData> page = PageableExecutionUtils.getPage(content, pageable, total);

        return PageData.fromPage(page);
    }

    @Override
    @Transactional
    public void closeAllOpenedSessions(Long testId) {
        testSessionRepository
                .findAllByTestId(testId, false, Pageable.unpaged())
                .forEach(session ->
                        applicationEventPublisher.publishEvent(new TestSessionClosedEvent(session.getId()))
                );
    }

    @NotNull
    @Transactional
    @Override
    public TestSessionData createSession(Long testId, @NotNull TestSessionCreateRequest request) {
        User user = this.getCurrentUser();

        var test = this.getTest(testId);

        TestSession entity = testSessionMapper.toEntity(request, test, user);
        testSessionRepository.save(entity);

        return testSessionMapper.toData(entity);
    }

    private Test getTest(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Тест не найден"));
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
