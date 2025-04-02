package com.testmaster.repository;

import com.testmaster.model.*;
        import com.testmaster.model.TestModel.TestModel;
import com.testmaster.model.TestModel.TestStatus;
import com.testmaster.model.UserModel.UserModel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserAnswerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Test
    public void testSaveUserAnswerModel() {
        UserModel user = new UserModel(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "activation-code",
                false,
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
                "Открытый вопрос"
        );
        entityManager.persist(type);

        QuestionModel question = new QuestionModel(
                test,
                type,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(question);

        UserAnswerModel answer = new UserAnswerModel(
                question,
                test,
                user,
                "Париж",
                true,
                1,
                LocalDateTime.now()
        );
        UserAnswerModel saved = userAnswerRepository.save(answer);

        assertNotNull(saved.getId());
        assertEquals("Париж", saved.getUserValue());
        assertTrue(saved.getIsCorrect());
        assertEquals(1, saved.getCountPoints());
    }

    @Test
    public void testSaveAndFindUserAnswerModel() {
        UserModel user = new UserModel(
                false,
                "Пётр Петров",
                "petr@example.com",
                "securepass",
                "activation-key",
                false,
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
        entityManager.persist(question);

        UserAnswerModel answer = new UserAnswerModel(
                question,
                test,
                user,
                "Берлин",
                false,
                0,
                LocalDateTime.now()
        );

        entityManager.persist(answer);

        Optional<UserAnswerModel> foundOpt = userAnswerRepository.findById(answer.getId());

        assertTrue(foundOpt.isPresent());

        UserAnswerModel found = foundOpt.get();
        assertEquals("Берлин", found.getUserValue());
        assertFalse(found.getIsCorrect());
        assertEquals(0, found.getCountPoints());
        assertEquals("Пётр Петров", found.getUser().getName());
        assertEquals("Тест по истории", found.getTest().getTitle());
    }
}