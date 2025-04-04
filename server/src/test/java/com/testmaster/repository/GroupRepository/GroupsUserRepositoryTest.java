package com.testmaster.repository.GroupRepository;

import api.domain.user.UserRoles;
import com.testmaster.model.GroupModel.GroupModel;
import com.testmaster.model.GroupModel.GroupsUserModel;
import api.domain.user.UserGroupsId;
import com.testmaster.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GroupsUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupsUserRepository groupsUserRepository;

    @Test
    public void testSaveGroupsUserModel() {
        UserModel user = new UserModel(
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

        GroupModel group = new GroupModel(
                user,
                "Группа A"
        );
        entityManager.persist(group);

        UserGroupsId id = new UserGroupsId(
                user.getId(),
                group.getId()
        );

        GroupsUserModel link = new GroupsUserModel(
                id,
                user,
                group
        );
        GroupsUserModel saved = groupsUserRepository.save(link);

        assertNotNull(saved.getId());
        assertEquals(user.getId(), saved.getUser().getId());
        assertEquals(group.getId(), saved.getGroup().getId());
    }

    @Test
    public void testSaveAndFindGroupsUserModel() {
        UserModel user = new UserModel(
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

        GroupModel group = new GroupModel(
                user,
                "Группа B"
        );
        entityManager.persist(group);

        UserGroupsId id = new UserGroupsId(
                user.getId(),
                group.getId()
        );

        GroupsUserModel link = new GroupsUserModel(
                id,
                user,
                group
        );
        entityManager.persist(link);

        Optional<GroupsUserModel> foundOpt = groupsUserRepository.findById(id);

        assertTrue(foundOpt.isPresent());
        GroupsUserModel found = foundOpt.get();
        assertEquals("Пётр Петров", found.getUser().getName());
        assertEquals("Группа B", found.getGroup().getTitle());
    }
}