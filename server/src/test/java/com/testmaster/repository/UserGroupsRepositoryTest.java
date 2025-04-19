package com.testmaster.repository;

import com.testmaster.model.Group.GroupUser;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Group.Group;
import com.testmasterapi.domain.group.GroupUserId;
import com.testmaster.model.User;
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
    private GroupUserRepository groupsUserRepository;

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

        GroupUserId id = new GroupUserId(
                user.getId(),
                group.getId()
        );

        GroupUser link = new GroupUser(
                id,
                user,
                group
        );
        GroupUser saved = groupsUserRepository.save(link);

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

        GroupUserId id = new GroupUserId(
                user.getId(),
                group.getId()
        );

        GroupUser link = new GroupUser(
                id,
                user,
                group
        );
        entityManager.persist(link);

        Optional<GroupUser> foundOpt = groupsUserRepository.findById(id);

        assertTrue(foundOpt.isPresent());
        GroupUser found = foundOpt.get();
        assertEquals("Пётр Петров", found.getUser().getName());
        assertEquals("Группа B", found.getGroup().getTitle());
    }
}