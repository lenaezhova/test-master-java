package com.testmaster.repository;

import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.TestModel;
import com.testmasterapi.domain.test.TestStatus;
import com.testmaster.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class TestRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TestRepository testRepository;

    @Test
    public void testSave() {
        UserModel user = new UserModel(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "abc123-activation",
                false,
                List.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        TestModel test = new TestModel(
                user,
                "Название теста",
                TestStatus.CLOSED,
                "Описание теста"
        );
        test.setOwner(user);
        test.setStatus(TestStatus.CLOSED);

        TestModel savedTest = testRepository.save(test);

        assertNotNull(savedTest.getId());
        assertEquals("Иван Иванов", savedTest.getOwner().getName());
        assertEquals("ivan@example.com", savedTest.getOwner().getEmail());
    }

    @Test
    public void testFindTest() {
        UserModel user = new UserModel(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "abc123-activation",
                false,
                List.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        entityManager.persist(user);

        TestModel test = new TestModel(
                user,
                "Название теста",
                TestStatus.CLOSED,
                "Описание теста"
        );
        test.setOwner(user);
        test.setStatus(TestStatus.OPEN);

        TestModel persistedTest = entityManager.persist(test);

        Optional<TestModel> foundOpt = testRepository.findById(persistedTest.getId());

        assertTrue(foundOpt.isPresent());

        TestModel found = foundOpt.get();
        assertEquals("Название теста", found.getTitle());
        assertEquals("Описание теста", found.getDescription());
        assertEquals(TestStatus.OPEN, found.getStatus());

        assertNotNull(found.getOwner());
        assertEquals("Иван Иванов", found.getOwner().getName());
        assertEquals("ivan@example.com", found.getOwner().getEmail());
    }
}