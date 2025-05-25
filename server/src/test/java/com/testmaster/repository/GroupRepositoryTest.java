package com.testmaster.repository;

import com.testmaster.repository.GroupRepository.GroupRepository;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Group;
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
public class GroupRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testSaveGroupModel() {
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

        Group saved = groupRepository.save(group);

        assertNotNull(saved.getId());
        assertEquals("Группа A", saved.getTitle());
        assertEquals("Иван Иванов", saved.getOwner().getName());
    }

    @Test
    public void testSaveAndFindGroupModel() {
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

        Optional<Group> foundOpt = groupRepository.findById(group.getId());

        assertTrue(foundOpt.isPresent());
        Group found = foundOpt.get();
        assertEquals("Группа B", found.getTitle());
        assertEquals("Пётр Петров", found.getOwner().getName());
    }
}