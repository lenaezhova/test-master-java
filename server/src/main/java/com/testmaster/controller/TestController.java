package com.testmaster.controller;

import com.testmasterapi.api.TestApi;
import com.testmaster.dto.TestDto;
import com.testmaster.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import com.testmaster.model.TestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.testmaster.service.TestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(TestApi.PATH)
public class TestController implements TestApi {
    private final TestMapper testMapper;

    private final TestService testService;

    @Override
    public ResponseEntity<List<TestDto>> all() {

        return ResponseEntity.ok(
                testService.getAll()
                        .stream()
                        .map(testMapper::toDto)
                        .toList()
        );
    }

    @Override
    public ResponseEntity<TestDto> one(@PathVariable Long id) {
        TestModel model = testService.getOne(id);

        return ResponseEntity.ok(testMapper.toDto(model));
    }

    @Override
    public ResponseEntity<Object> create(@RequestBody TestModel test) {
        testService.addTest(test.getTitle(), test.getDescription());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody TestModel test) {
        testService.updateTest(id, test);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        testService.deleteTest(id);

        return ResponseEntity.ok().build();
    }
}
