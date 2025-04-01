package com.testmaster.controller;

import lombok.RequiredArgsConstructor;
import com.testmaster.model.Tests.Tests;
import org.springframework.web.bind.annotation.*;
import com.testmaster.service.TestsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tests")
public class TestsController {

    private final TestsService testsService;

    @GetMapping
    public List<Tests> all() {
        return testsService.getAll();
    }

    @GetMapping("/{id}")
    public Tests one(@PathVariable Long id) {
        return testsService.getOne(id);
    }

    @PostMapping
    public Tests create(@RequestBody Tests test) {
        return testsService.addTest(test.getTitle(), test.getDescription());
    }

    @PutMapping("/{id}")
    public Tests create(@PathVariable Long id, @RequestBody Tests test) {
        return testsService.updateTest(id, test);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        testsService.deleteTest(id);
    }
}
