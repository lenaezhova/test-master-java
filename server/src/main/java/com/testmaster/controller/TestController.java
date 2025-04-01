package com.testmaster.controller;

import com.testmaster.dto.TestDto;
import com.testmaster.mapper.test.TestMapper;
import lombok.RequiredArgsConstructor;
import com.testmaster.model.TestModel.TestModel;
import org.springframework.web.bind.annotation.*;
import com.testmaster.service.TestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    private final TestMapper testMapper;

    private final TestService testService;

    @GetMapping
    public List<TestDto> all() {
        return testService.getAll()
                .stream()
                .map(testMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public TestDto one(@PathVariable Long id) {
        TestModel model = testService.getOne(id);
        return testMapper.toDto(model);
    }

    @PostMapping
    public TestModel create(@RequestBody TestModel test) {
        return testService.addTest(test.getTitle(), test.getDescription());
    }

    @PutMapping("/{id}")
    public TestModel create(@PathVariable Long id, @RequestBody TestModel test) {
        return testService.updateTest(id, test);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        testService.deleteTest(id);
    }
}
