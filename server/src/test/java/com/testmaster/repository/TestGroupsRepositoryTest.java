package com.testmaster.repository;

import com.testmaster.model.Test.TestGroup;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Group;
import com.testmasterapi.domain.test.TestGroupId;
import com.testmaster.model.Test.Test;
import com.testmasterapi.domain.test.TestStatus;
import com.testmaster.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestGroupsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TestGroupRepository groupsTestRepository;

    @org.junit.jupiter.api.Test
    public void testSaveGroupsTestModel() {
        User user = new User(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "activation-code",
                false,
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Test test = new Test();
        test.setOwner(user);
        test.setTitle("Тест A");
        test.setStatus(TestStatus.CLOSED);
        test.setDescription("Описание теста");

        entityManager.persist(test);

        Group group = new Group(
                user,
                "Группа A"
        );
        entityManager.persist(group);

        TestGroupId id = new TestGroupId(
                test.getId(),
                group.getId()
        );

        TestGroup link = new TestGroup(
                id,
                test,
                group
        );

        TestGroup saved = groupsTestRepository.save(link);

        assertNotNull(saved.getId());
        assertEquals(test.getId(), saved.getTest().getId());
        assertEquals(group.getId(), saved.getGroup().getId());
        assertEquals("Иван Иванов", saved.getGroup().getOwner().getName());
    }

    @org.junit.jupiter.api.Test
    public void testSaveAndFindGroupsTestModel() {
        User user = new User(
                false,
                "Пётр Петров",
                "petr@example.com",
                "securepass",
                "activation-key",
                false,
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Test test = new Test();
        test.setOwner(user);
        test.setTitle("Тест B");
        test.setStatus(TestStatus.CLOSED);
        test.setDescription("Описание теста");

        entityManager.persist(test);

        Group group = new Group(
                user,
                "Группа B"
        );
        entityManager.persist(group);

        TestGroupId id = new TestGroupId(
                test.getId(),
                group.getId()
        );

        TestGroup link = new TestGroup(
                id,
                test,
                group
        );
        entityManager.persist(link);

        Optional<TestGroup> foundOpt = groupsTestRepository.findById(id);
        assertTrue(foundOpt.isPresent());

        TestGroup found = foundOpt.get();
        assertEquals("Тест B", found.getTest().getTitle());
        assertEquals("Группа B", found.getGroup().getTitle());
        assertEquals("Пётр Петров", found.getGroup().getOwner().getName());
    }
}