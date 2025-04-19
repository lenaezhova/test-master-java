package com.testmaster.repository;

import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Test;
import com.testmasterapi.domain.test.TestStatus;
import com.testmaster.model.TypeQuestion;
import com.testmaster.model.Question;
import com.testmaster.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    @org.junit.jupiter.api.Test
    public void testSaveQuestionModel() {
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

        Question saved = questionRepository.save(question);

        assertNotNull(saved.getId());
        assertEquals("Открытый вопрос", saved.getType().getTitle());
        assertEquals("Тест по истории", saved.getTest().getTitle());
    }

    @org.junit.jupiter.api.Test
    public void testFindQuestionModel() {
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
                "Тест по географии",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        TypeQuestion type = new TypeQuestion(
                "Множественный выбор"
        );
        entityManager.persist(type);

        Question question = new Question(
                test,
                type,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(question);

        Optional<Question> foundOpt = questionRepository.findById(question.getId());

        assertTrue(foundOpt.isPresent());
        Question found = foundOpt.get();

        assertEquals("Множественный выбор", found.getType().getTitle());
        assertEquals("Тест по географии", found.getTest().getTitle());
    }
}