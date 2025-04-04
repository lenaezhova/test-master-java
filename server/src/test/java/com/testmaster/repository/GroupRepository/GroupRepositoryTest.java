package com.testmaster.repository.GroupRepository;

import api.domain.user.UserRoles;
import com.testmaster.model.GroupModel.GroupModel;
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
public class GroupRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testSaveGroupModel() {
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

        GroupModel saved = groupRepository.save(group);

        assertNotNull(saved.getId());
        assertEquals("Группа A", saved.getTitle());
        assertEquals("Иван Иванов", saved.getOwner().getName());
    }

    @Test
    public void testSaveAndFindGroupModel() {
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

        Optional<GroupModel> foundOpt = groupRepository.findById(group.getId());

        assertTrue(foundOpt.isPresent());
        GroupModel found = foundOpt.get();
        assertEquals("Группа B", found.getTitle());
        assertEquals("Пётр Петров", found.getOwner().getName());
    }
}