package com.testmaster.service.TestSessionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.GroupMapper;
import com.testmaster.mapper.TestSessionMapper;
import com.testmaster.model.Group.Group;
import com.testmaster.model.Test;
import com.testmaster.model.TestSession;
import com.testmaster.model.User;
import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.group.data.GroupData;
import com.testmasterapi.domain.group.request.GroupCreateRequest;
import com.testmasterapi.domain.group.request.GroupUpdateRequest;
import com.testmasterapi.domain.testSession.data.TestSessionData;
import com.testmasterapi.domain.testSession.request.TestSessionCreateRequest;
import com.testmasterapi.domain.testSession.request.TestSessionUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTestSessionService implements TestSessionService {
    private final TestSessionMapper testSessionMapper;

    private final TestSessionRepository testSessionRepository;
    private final UserRepository userRepository;
    private final TestRepository testRepository;

    private final String notFoundTestSessionMessage = "Сессия теста не найдена";

    @Override
    public List<TestSessionData> getAll() {
        return testSessionRepository.findAll()
                .stream()
                .map(testSessionMapper::toData)
                .toList();
    }

    @Override
    public List<TestSessionData> getAllByUserId(Long userId) {
        return testSessionRepository.findAllByUserId(userId)
                .stream()
                .map(testSessionMapper::toData)
                .toList();
    }

    @Override
    public TestSessionData getOne(Long testSessionId) {
        var data = this.getTestSession(testSessionId);

        return testSessionMapper.toData(data);
    }

    @NotNull
    @Transactional
    @Override
    public TestSessionData create(Long testId, @NotNull TestSessionCreateRequest request) {
        User user = this.getCurrentUser();

        Test test = this.getTest(testId);

        TestSession entity = testSessionMapper.toEntity(request, test, user);
        testSessionRepository.save(entity);

        return testSessionMapper.toData(entity);
    }

    @Override
    @Transactional
    public void update(Long testSessionId, TestSessionUpdateRequest request) {
        TestSession session = this.getTestSession(testSessionId);

        if (session.getClosedAt() == null) {
            LocalDateTime now = LocalDateTime.now();
            request.setClosedAt(now);
        }

        int updated = testSessionRepository.update(testSessionId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundTestSessionMessage);
        }
    }

    @Override
    @Transactional
    public void delete(Long testSessionId) {
        int deleted = testSessionRepository.delete(testSessionId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundTestSessionMessage);
        }
    }

    private Test getTest(Long testId) {
        return testRepository
                .findTestById(testId)
                .orElseThrow(() -> new NotFoundException("Тест с таким идентификатором не найден"));
    }

    private User getUser(Long userId) {
        return userRepository
                .findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким идентификатором не найден"));
    }

    private TestSession getTestSession(Long testSessionId) {
        return testSessionRepository
                .findById(testSessionId)
                .orElseThrow(() -> new NotFoundException(notFoundTestSessionMessage));
    }

    private User getCurrentUser() {
        var customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.getUser(customUserDetails.getId());
    }
}
