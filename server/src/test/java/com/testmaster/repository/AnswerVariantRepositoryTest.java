package com.testmaster.repository;

import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.AnswerVariant;
import com.testmaster.model.Test;
import com.testmasterapi.domain.test.TestStatus;
import com.testmaster.model.QuestionType;
import com.testmaster.model.Question;
import com.testmaster.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Test test = new Test();
        test.setOwner(user);
        test.setTitle("Тест по математике");
        test.setStatus(TestStatus.CLOSED);
        test.setDescription("Описание");

        entityManager.persist(test);

        QuestionType type = new QuestionType();
        type.setTitle("Один выбор");
        type.setTypes(Set.of(QuestionTypes.SINGLE));
        entityManager.persist(type);

        Question question = new Question();
        question.setSavedType(QuestionTypes.TEXT);
        question.setTest(test);
        question.setType(type);
        question.setCreatedAt(LocalDateTime.now());
        entityManager.persist(question);

        AnswerVariant variant = new AnswerVariant();
        variant.setQuestion(question);
        variant.setTitle("42");
        variant.setIsCorrect(true);
        variant.setCountPoints(1);

        AnswerVariant saved = answerVariantRepository.save(variant);

        assertNotNull(saved.getId());
        assertEquals("42", saved.getTitle());
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
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Test test = new Test();
        test.setOwner(user);
        test.setTitle("Тест по биологии");
        test.setStatus(TestStatus.CLOSED);
        test.setDescription("Описание");

        entityManager.persist(test);

        QuestionType type = new QuestionType();
        type.setTitle("Множественный выбор");
        type.setTypes(Set.of(QuestionTypes.MULTIPLE));
        entityManager.persist(type);

        Question question = new Question();
        question.setSavedType(QuestionTypes.TEXT);
        question.setTest(test);
        question.setType(type);
        question.setCreatedAt(LocalDateTime.now());
        entityManager.persist(question);

        AnswerVariant variant = new AnswerVariant();
        variant.setQuestion(question);
        variant.setTitle("Клетка");
        variant.setIsCorrect(false);
        variant.setCountPoints(0);
        entityManager.persist(variant);

        Optional<AnswerVariant> foundOpt = answerVariantRepository.findById(variant.getId());

        assertTrue(foundOpt.isPresent());
        AnswerVariant found = foundOpt.get();

        assertEquals("Клетка", found.getTitle());
        assertFalse(found.getIsCorrect());
        assertEquals(0, found.getCountPoints());
        assertNotNull(found.getQuestion());
        assertEquals("Множественный выбор", found.getQuestion().getType().getTitle());
    }
}