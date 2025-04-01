package com.testmaster.service;

import com.testmaster.model.TestModel.TestStatus;
import com.testmaster.model.TestModel.TestModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestServiceTest {
    @Test
    void testGetAllTests() {
        Test1Service testsService = new Test1Service();
        List<TestModel> tests = testsService.getTests();

        assertNotNull(tests);
        assertEquals(6, tests.size());
    }

    @Test
    void testAddTest() {
        Test1Service testsService = new Test1Service();

        testsService.add(new TestModel(null, null, "title7", TestStatus.CLOSED, "description7"));

        List<TestModel> tests = testsService.getTests();
        assertEquals(7, tests.size());
    }

    @Test
    void testDeleteTest() {
        Test1Service testsService = new Test1Service();

        testsService.delete(6L);

        List<TestModel> tests = testsService.getTests();
        assertEquals(5, tests.size());
    }
}