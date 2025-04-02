package com.testmaster.service;

import com.testmaster.mapper.test.TestMapper;
import lombok.RequiredArgsConstructor;
import com.testmaster.model.TestModel.TestModel;
import org.springframework.stereotype.Service;
import com.testmaster.repository.TestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestMapper testMapper;

    private final TestRepository testRepository;

    public List<TestModel> getAll() {
        return testRepository.findAll();
    }

    public TestModel getOne(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    public TestModel addTest(String title, String description) {
        TestModel test = new TestModel();
        test.setTitle(title);


        test.setDescription(description);
        return testRepository.save(test);
    }

    public TestModel updateTest(Long id, TestModel test) {
        return testRepository.findById(id)
                .map(existing -> {
                    testMapper.update(existing, test);
                    return testRepository.save(existing);
                })
                .orElse(null);
    }

    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}
