package com.testmaster.service.TestService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.TestMapper;
import com.testmaster.model.User.User;
import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.testmaster.model.Test;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.testmaster.repository.TestRepository.TestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTestService implements TestService {
    private final TestMapper testMapper;

    private final TestRepository testRepository;
    private final UserRepository userRepository;

    @Override
    public List<TestData> getAll() {
        return testRepository
                .findAllTests()
                .stream()
                .map(testMapper::toTestData)
                .toList();
    }

    @Override
    public TestData getOne(Long id) {
        Test test = testRepository
                .findTestById(id)
                .orElseThrow(() -> new NotFoundException("Тест не найден"));

        return testMapper.toTestData(test);
    }

    @NotNull
    @Transactional
    @Override
    public TestData create(@NotNull TestCreateRequest request) {
        User user = userRepository
                .findUserById(this.getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Test test = testMapper.toEntity(request, user);
        testRepository.save(test);

        return testMapper.toTestData(test);
    }

    @Override
    @Transactional
    public void update(Long id, TestUpdateRequest updateRequest) {
        int updated = testRepository.update(id, updateRequest);
        if (updated == 0) {
            throw new NotFoundException("Тест не найден");
        }
    }

    private Long getUserId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUserDetails.getId();
    }
}
