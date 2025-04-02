package com.testmaster.repository.GroupRepository;

import com.testmaster.model.GroupModel.GroupModel;
import com.testmaster.model.GroupModel.GroupsTestModel;
import com.testmaster.model.TestModel.TestGroupsId;
import com.testmaster.model.TestModel.TestModel;
import com.testmaster.model.TestModel.TestStatus;
import com.testmaster.model.UserModel.UserModel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GroupsTestRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupsTestRepository groupsTestRepository;

    @Test
    public void testSaveGroupsTestModel() {
        UserModel user = new UserModel(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "activation-code",
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        TestModel test = new TestModel(
                user,
                "Тест A",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        GroupModel group = new GroupModel(
                user,
                "Группа A"
        );
        entityManager.persist(group);

        TestGroupsId id = new TestGroupsId(
                test.getId(),
                group.getId()
        );

        GroupsTestModel link = new GroupsTestModel(
                id,
                test,
                group
        );

        GroupsTestModel saved = groupsTestRepository.save(link);

        assertNotNull(saved.getId());
        assertEquals(test.getId(), saved.getTest().getId());
        assertEquals(group.getId(), saved.getGroup().getId());
        assertEquals("Иван Иванов", saved.getGroup().getOwner().getName());
    }

    @Test
    public void testSaveAndFindGroupsTestModel() {
        UserModel user = new UserModel(
                false,
                "Пётр Петров",
                "petr@example.com",
                "securepass",
                "activation-key",
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        TestModel test = new TestModel(
                user,
                "Тест B",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        GroupModel group = new GroupModel(
                user,
                "Группа B"
        );
        entityManager.persist(group);

        TestGroupsId id = new TestGroupsId(
                test.getId(),
                group.getId()
        );

        GroupsTestModel link = new GroupsTestModel(
                id,
                test,
                group
        );
        entityManager.persist(link);

        Optional<GroupsTestModel> foundOpt = groupsTestRepository.findById(id);
        assertTrue(foundOpt.isPresent());

        GroupsTestModel found = foundOpt.get();
        assertEquals("Тест B", found.getTest().getTitle());
        assertEquals("Группа B", found.getGroup().getTitle());
        assertEquals("Пётр Петров", found.getGroup().getOwner().getName());
    }
}