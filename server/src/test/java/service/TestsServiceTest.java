package service;

import model.Tests.TestStatus;
import model.Tests.Tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestsServiceTest {
    @Test
    void testGetAllTests() {
        TestsService testsService = new TestsService();
        List<Tests> tests = testsService.getTests();

        assertNotNull(tests);
        assertEquals(6, tests.size());
    }

    @Test
    void testAddTest() {
        TestsService testsService = new TestsService();

        testsService.add(new Tests(null, null, "title7", TestStatus.CLOSED, "description7"));

        List<Tests> tests = testsService.getTests();
        assertEquals(7, tests.size());
    }

    @Test
    void testDeleteTest() {
        TestsService testsService = new TestsService();

        testsService.delete(6L);

        List<Tests> tests = testsService.getTests();
        assertEquals(5, tests.size());
    }
}