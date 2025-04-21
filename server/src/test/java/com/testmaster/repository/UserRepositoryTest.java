package com.testmaster.repository;

import com.testmaster.repository.UserRepository.UserRepository;
import com.testmasterapi.domain.user.UserRoles;
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
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveUser() {
        User user = new User(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "abc123-activation",
                true,
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("Иван Иванов", savedUser.getName());
        assertEquals("ivan@example.com", savedUser.getEmail());
        assertTrue(savedUser.getIsActivate());
    }

    @Test
    public void testFindUser() {
        User user = new User(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "abc123-activation",
                false,
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        User persisted = entityManager.persist(user);

        Optional<User> foundUserOpt = userRepository.findUserById(persisted.getId());

        assertTrue(foundUserOpt.isPresent());

        User foundUser = foundUserOpt.get();
        assertEquals("Иван Иванов", foundUser.getName());
        assertEquals("ivan@example.com", foundUser.getEmail());
        assertFalse(foundUser.getIsActivate());
    }
}