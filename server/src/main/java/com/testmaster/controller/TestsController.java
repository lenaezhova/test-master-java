package com.testmaster.controller;

import lombok.RequiredArgsConstructor;
import com.testmaster.model.Tests.Tests;
import org.hibernate.mapping.List;
import org.springframework.web.bind.annotation.*;
import com.testmaster.service.TestsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tests")
public class TestsController {

    private final TestsService testsService;

    @GetMapping
    public List<Tests> all() {
        return testsService.getAll();
    }

    @PostMapping
    public Tests create(@RequestBody Tests test) {
        return testsService.addTest(test.getTitle(), test.getDescription());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        testsService.deleteTest(id);
    }
}
