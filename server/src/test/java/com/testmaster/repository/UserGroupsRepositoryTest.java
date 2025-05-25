package com.testmaster.repository;

import com.testmaster.model.User.UserGroup;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Group;
import com.testmasterapi.domain.user.UserGroupId;
import com.testmaster.model.User.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserGroupsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserGroupRepository groupsUserRepository;

    @Test
    public void testSaveGroupsUserModel() {
        User user = new User(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "activation-code",
                false,
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Group group = new Group(
                user,
                "Группа A"
        );
        entityManager.persist(group);

        UserGroupId id = new UserGroupId(
                user.getId(),
                group.getId()
        );

        UserGroup link = new UserGroup(
                id,
                user,
                group
        );
        UserGroup saved = groupsUserRepository.save(link);

        assertNotNull(saved.getId());
        assertEquals(user.getId(), saved.getUser().getId());
        assertEquals(group.getId(), saved.getGroup().getId());
    }

    @Test
    public void testSaveAndFindGroupsUserModel() {
        User user = new User(
                false,
                "Пётр Петров",
                "petr@example.com",
                "securepass",
                "activation-key",
                false,
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Group group = new Group(
                user,
                "Группа B"
        );
        entityManager.persist(group);

        UserGroupId id = new UserGroupId(
                user.getId(),
                group.getId()
        );

        UserGroup link = new UserGroup(
                id,
                user,
                group
        );
        entityManager.persist(link);

        Optional<UserGroup> foundOpt = groupsUserRepository.findById(id);

        assertTrue(foundOpt.isPresent());
        UserGroup found = foundOpt.get();
        assertEquals("Пётр Петров", found.getUser().getName());
        assertEquals("Группа B", found.getGroup().getTitle());
    }
}