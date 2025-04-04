package com.testmaster.repository;

import api.domain.user.UserRoles;
import com.testmaster.model.TestModel;
import api.domain.test.TestStatus;
import com.testmaster.model.TypeQuestionModel;
import com.testmaster.model.QuestionModel;
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
public class QuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testSaveQuestionModel() {
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

        TestModel test = new TestModel(
                user,
                "Тест по истории",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        TypeQuestionModel type = new TypeQuestionModel(
                "Открытый вопрос"
        );
        entityManager.persist(type);

        QuestionModel question = new QuestionModel(
                test,
                type,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        QuestionModel saved = questionRepository.save(question);

        assertNotNull(saved.getId());
        assertEquals("Открытый вопрос", saved.getType().getTitle());
        assertEquals("Тест по истории", saved.getTest().getTitle());
    }

    @Test
    public void testFindQuestionModel() {
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

        TestModel test = new TestModel(
                user,
                "Тест по географии",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        TypeQuestionModel type = new TypeQuestionModel(
                "Множественный выбор"
        );
        entityManager.persist(type);

        QuestionModel question = new QuestionModel(
                test,
                type,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(question);

        Optional<QuestionModel> foundOpt = questionRepository.findById(question.getId());

        assertTrue(foundOpt.isPresent());
        QuestionModel found = foundOpt.get();

        assertEquals("Множественный выбор", found.getType().getTitle());
        assertEquals("Тест по географии", found.getTest().getTitle());
    }
}