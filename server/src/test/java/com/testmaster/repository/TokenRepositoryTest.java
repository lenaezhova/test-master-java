package com.testmaster.repository;

import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Token;
import com.testmaster.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void testSave() {
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
        entityManager.persist(user);

        Token token = new Token(
                user,
                "simple-token-123"
        );

        Token saved = tokenRepository.save(token);

        assertNotNull(saved.getId());
        assertEquals("simple-token-123", saved.getRefreshToken());
        assertNotNull(saved.getUser());
        assertEquals("Иван Иванов", saved.getUser().getName());
    }


    @Test
    public void testFindToken() {
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
        entityManager.persist(user);

        Token token = new Token(
                user,
                "simple-token-123"
        );

        entityManager.persist(token);

        Optional<Token> foundOpt = tokenRepository.findById(token.getId());

        assertTrue(foundOpt.isPresent());

        Token found = foundOpt.get();
        assertEquals("simple-token-123", found.getRefreshToken());
        assertNotNull(found.getUser());
        assertEquals("Иван Иванов", found.getUser().getName());
    }
}