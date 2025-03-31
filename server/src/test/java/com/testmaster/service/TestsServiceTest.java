package com.testmaster.service;

import com.testmaster.model.Tests.TestStatus;
import com.testmaster.model.Tests.Tests;
import com.testmaster.service.Tests1Service;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestsServiceTest {
    @Test
    void testGetAllTests() {
        Tests1Service testsService = new Tests1Service();
        List<Tests> tests = testsService.getTests();

        assertNotNull(tests);
        assertEquals(6, tests.size());
    }

    @Test
    void testAddTest() {
        Tests1Service testsService = new Tests1Service();

        testsService.add(new Tests(null, null, "title7", TestStatus.CLOSED, "description7"));

        List<Tests> tests = testsService.getTests();
        assertEquals(7, tests.size());
    }

    @Test
    void testDeleteTest() {
        Tests1Service testsService = new Tests1Service();

        testsService.delete(6L);

        List<Tests> tests = testsService.getTests();
        assertEquals(5, tests.size());
    }
}