package com.testmaster.repository;

import com.testmasterapi.domain.user.UserRoles;
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
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveUser() {
        UserModel user = new UserModel(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "abc123-activation",
                true,
                List.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        UserModel savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("Иван Иванов", savedUser.getName());
        assertEquals("ivan@example.com", savedUser.getEmail());
        assertTrue(savedUser.getIsActivate());
    }

    @Test
    public void testFindUser() {
        UserModel user = new UserModel(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "abc123-activation",
                false,
                List.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        UserModel persisted = entityManager.persist(user);

        Optional<UserModel> foundUserOpt = userRepository.findById(persisted.getId());

        assertTrue(foundUserOpt.isPresent());

        UserModel foundUser = foundUserOpt.get();
        assertEquals("Иван Иванов", foundUser.getName());
        assertEquals("ivan@example.com", foundUser.getEmail());
        assertFalse(foundUser.getIsActivate());
    }
}