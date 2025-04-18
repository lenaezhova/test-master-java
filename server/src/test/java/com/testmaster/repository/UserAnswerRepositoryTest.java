package com.testmaster.repository;

import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.*;
        import com.testmaster.model.Test;
import com.testmasterapi.domain.test.TestStatus;
import com.testmaster.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserAnswerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @org.junit.jupiter.api.Test
    public void testSaveUserAnswerModel() {
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

        Test test = new Test(
                user,
                "Тест по географии",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        TypeQuestion type = new TypeQuestion(
                "Открытый вопрос"
        );
        entityManager.persist(type);

        Question question = new Question(
                test,
                type,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(question);

        UserAnswer answer = new UserAnswer(
                question,
                test,
                user,
                "Париж",
                true,
                1,
                LocalDateTime.now()
        );
        UserAnswer saved = userAnswerRepository.save(answer);

        assertNotNull(saved.getId());
        assertEquals("Париж", saved.getUserValue());
        assertTrue(saved.getIsCorrect());
        assertEquals(1, saved.getCountPoints());
    }

    @org.junit.jupiter.api.Test
    public void testSaveAndFindUserAnswerModel() {
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

        Test test = new Test(
                user,
                "Тест по истории",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        TypeQuestion type = new TypeQuestion(
                "Открытый вопрос"
        );
        entityManager.persist(type);

        Question question = new Question(
                test,
                type,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(question);

        UserAnswer answer = new UserAnswer(
                question,
                test,
                user,
                "Берлин",
                false,
                0,
                LocalDateTime.now()
        );

        entityManager.persist(answer);

        Optional<UserAnswer> foundOpt = userAnswerRepository.findById(answer.getId());

        assertTrue(foundOpt.isPresent());

        UserAnswer found = foundOpt.get();
        assertEquals("Берлин", found.getUserValue());
        assertFalse(found.getIsCorrect());
        assertEquals(0, found.getCountPoints());
        assertEquals("Пётр Петров", found.getUser().getName());
        assertEquals("Тест по истории", found.getTest().getTitle());
    }
}