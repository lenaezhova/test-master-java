package com.testmaster.repository;

import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.AnswerVariantModel;
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
public class AnswerVariantRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnswerVariantRepository answerVariantRepository;

    @org.junit.jupiter.api.Test
    public void testSaveAnswerVariantModel() {
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
                "Тест по математике",
                TestStatus.CLOSED,
                "Описание"
        );
        entityManager.persist(test);

        TypeQuestion type = new TypeQuestion(
                "Один выбор"
        );
        entityManager.persist(type);

        Question question = new Question(
                test,
                type,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(question);

        AnswerVariantModel variant = new AnswerVariantModel(
                question,
                "42",
                true,
                1
        );
        AnswerVariantModel saved = answerVariantRepository.save(variant);

        assertNotNull(saved.getId());
        assertEquals("42", saved.getText());
        assertTrue(saved.getIsCorrect());
        assertEquals(1, saved.getCountPoints());
    }

    @org.junit.jupiter.api.Test
    public void testSaveAndFindAnswerVariantModel() {
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
                "Тест по биологии",
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

        AnswerVariantModel variant = new AnswerVariantModel(
                question,
                "Клетка",
                false,
                0
        );
        entityManager.persist(variant);

        Optional<AnswerVariantModel> foundOpt = answerVariantRepository.findById(variant.getId());

        assertTrue(foundOpt.isPresent());
        AnswerVariantModel found = foundOpt.get();

        assertEquals("Клетка", found.getText());
        assertFalse(found.getIsCorrect());
        assertEquals(0, found.getCountPoints());
        assertNotNull(found.getQuestion());
        assertEquals("Множественный выбор", found.getQuestion().getType().getTitle());
    }
}