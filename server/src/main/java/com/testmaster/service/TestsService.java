package com.testmaster.service;

import lombok.RequiredArgsConstructor;
import com.testmaster.model.Tests.TestStatus;
import com.testmaster.model.Tests.Tests;
import org.springframework.stereotype.Service;
import com.testmaster.repository.TestsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestsService {

    private final TestsRepository testsRepository;

    public List<Tests> getAll() {
        return testsRepository.findAll();
    }

    public Tests addTest(String title, String description) {
        Tests test = new Tests(null, null, title, TestStatus.CLOSED, description);
        return testsRepository.save(test);
    }

    public void deleteTest(Long id) {
        testsRepository.deleteById(id);
    }
}
