package com.testmaster.repository;

import com.testmaster.model.User.UserGroups;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Group.Group;
import com.testmasterapi.domain.user.UserGroupsId;
import com.testmaster.model.User.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserGroupsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserGroupsRepository groupsUserRepository;

    @Test
    public void testSaveGroupsUserModel() {
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

        Group group = new Group(
                user,
                "Группа A"
        );
        entityManager.persist(group);

        UserGroupsId id = new UserGroupsId(
                user.getId(),
                group.getId()
        );

        UserGroups link = new UserGroups(
                id,
                user,
                group
        );
        UserGroups saved = groupsUserRepository.save(link);

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
                List.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Group group = new Group(
                user,
                "Группа B"
        );
        entityManager.persist(group);

        UserGroupsId id = new UserGroupsId(
                user.getId(),
                group.getId()
        );

        UserGroups link = new UserGroups(
                id,
                user,
                group
        );
        entityManager.persist(link);

        Optional<UserGroups> foundOpt = groupsUserRepository.findById(id);

        assertTrue(foundOpt.isPresent());
        UserGroups found = foundOpt.get();
        assertEquals("Пётр Петров", found.getUser().getName());
        assertEquals("Группа B", found.getGroup().getTitle());
    }
}