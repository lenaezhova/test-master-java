package com.testmaster.repository;

import com.testmaster.repository.TestRepository.TestRepository;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Test;
import com.testmasterapi.domain.test.TestStatus;
import com.testmaster.model.User.User;
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

    @org.junit.jupiter.api.Test
    public void testSave() {
        User user = new User(
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

        Test test = new Test(
                user,
                "Название теста",
                TestStatus.CLOSED,
                "Описание теста"
        );
        test.setOwner(user);
        test.setStatus(TestStatus.CLOSED);

        Test savedTest = testRepository.save(test);

        assertNotNull(savedTest.getId());
        assertEquals("Иван Иванов", savedTest.getOwner().getName());
        assertEquals("ivan@example.com", savedTest.getOwner().getEmail());
    }

    @org.junit.jupiter.api.Test
    public void testFindTest() {
        User user = new User(
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

        Test test = new Test(
                user,
                "Название теста",
                TestStatus.CLOSED,
                "Описание теста"
        );
        test.setOwner(user);
        test.setStatus(TestStatus.OPEN);

        Test persistedTest = entityManager.persist(test);

        Optional<Test> foundOpt = testRepository.findById(persistedTest.getId());

        assertTrue(foundOpt.isPresent());

        Test found = foundOpt.get();
        assertEquals("Название теста", found.getTitle());
        assertEquals("Описание теста", found.getDescription());
        assertEquals(TestStatus.OPEN, found.getStatus());

        assertNotNull(found.getOwner());
        assertEquals("Иван Иванов", found.getOwner().getName());
        assertEquals("ivan@example.com", found.getOwner().getEmail());
    }
}