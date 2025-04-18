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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.testmaster.repository.TestRepository.TestRepository;
import org.springframework.web.server.ResponseStatusException;

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
                .findAll()
                .stream()
                .map(testMapper::toTestData)
                .toList();
    }

    @Override
    public TestData getOne(Long id) {
        Test test = testRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        return testMapper.toTestData(test);
    }

    @NotNull
    @Transactional
    @Override
    public TestData create(TestCreateRequest createRequest) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getId();

        Test test = new Test();
        test.setTitle(createRequest.title());
        test.setDescription(createRequest.description());

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь с таким идентификатором не найден"));

        test.setOwner(user);
        testRepository.save(test);

        return testMapper.toTestData(test);
    }

    @Override
    @Transactional
    public void update(Long id, TestUpdateRequest updateRequest) {
        int updated = testRepository.update(id, updateRequest);
        if (updated == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        int deleted = testRepository.delete(id);
        if (deleted == 0) {
            throw new NotFoundException();
        }
    }
}
