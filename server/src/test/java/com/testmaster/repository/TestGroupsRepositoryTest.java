package com.testmaster.repository;

import com.testmaster.model.Group.GroupTests;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Group.Group;
import com.testmasterapi.domain.test.TestGroupsId;
import com.testmaster.model.Test;
import com.testmasterapi.domain.test.TestStatus;
import com.testmaster.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestGroupsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TestGroupsRepository groupsTestRepository;

    @org.junit.jupiter.api.Test
    public void testSaveGroupsTestModel() {
        User user = new User(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "activation-code",
                false,
                List.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Test test = new Test(
                user,
                "Тест A",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        Group group = new Group(
                user,
                "Группа A"
        );
        entityManager.persist(group);

        TestGroupsId id = new TestGroupsId(
                test.getId(),
                group.getId()
        );

        GroupTests link = new GroupTests(
                id,
                test,
                group
        );

        GroupTests saved = groupsTestRepository.save(link);

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
                List.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Test test = new Test(
                user,
                "Тест B",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        Group group = new Group(
                user,
                "Группа B"
        );
        entityManager.persist(group);

        TestGroupsId id = new TestGroupsId(
                test.getId(),
                group.getId()
        );

        GroupTests link = new GroupTests(
                id,
                test,
                group
        );
        entityManager.persist(link);

        Optional<GroupTests> foundOpt = groupsTestRepository.findById(id);
        assertTrue(foundOpt.isPresent());

        GroupTests found = foundOpt.get();
        assertEquals("Тест B", found.getTest().getTitle());
        assertEquals("Группа B", found.getGroup().getTitle());
        assertEquals("Пётр Петров", found.getGroup().getOwner().getName());
    }
}