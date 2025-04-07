package com.testmaster.repository;

import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.TokenModel;
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
public class TokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void testSave() {
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
        entityManager.persist(user);

        TokenModel token = new TokenModel(
                user,
                "simple-token-123"
        );

        TokenModel saved = tokenRepository.save(token);

        assertNotNull(saved.getId());
        assertEquals("simple-token-123", saved.getRefreshToken());
        assertNotNull(saved.getUser());
        assertEquals("Иван Иванов", saved.getUser().getName());
    }


    @Test
    public void testFindToken() {
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
        entityManager.persist(user);

        TokenModel token = new TokenModel(
                user,
                "simple-token-123"
        );

        entityManager.persist(token);

        Optional<TokenModel> foundOpt = tokenRepository.findById(token.getId());

        assertTrue(foundOpt.isPresent());

        TokenModel found = foundOpt.get();
        assertEquals("simple-token-123", found.getRefreshToken());
        assertNotNull(found.getUser());
        assertEquals("Иван Иванов", found.getUser().getName());
    }
}