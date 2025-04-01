package com.testmaster.service;

import com.testmaster.mapper.TestModelMapper;
import lombok.RequiredArgsConstructor;
import com.testmaster.model.TestModel.TestStatus;
import com.testmaster.model.TestModel.TestModel;
import org.springframework.stereotype.Service;
import com.testmaster.repository.TestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestModelMapper testModelMapper;

    private final TestRepository testRepository;

    public List<TestModel> getAll() {
        return testRepository.findAll();
    }

    public TestModel getOne(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    public TestModel addTest(String title, String description) {
        TestModel test = new TestModel(null, null, title, TestStatus.CLOSED, description);
        return testRepository.save(test);
    }

    public TestModel updateTest(Long id, TestModel test) {
        return testRepository.findById(id)
                .map(existing -> {
                    testModelMapper.update(existing, test);
                    return testRepository.save(existing);
                })
                .orElse(null);
    }

    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}
