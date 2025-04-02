package com.testmaster.repository;

import com.testmaster.model.AnswerVariantModel;
import com.testmaster.model.TestModel.TestModel;
import com.testmaster.model.TypeQuestionModel;
import com.testmaster.model.QuestionModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AnswerVariantRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnswerVariantRepository answerVariantRepository;

    @Test
    public void testSaveAnswerVariantModel() {
        TestModel test = new TestModel(
                "Тест по математике",
                "Описание"
        );
        entityManager.persist(test);

        TypeQuestionModel type = new TypeQuestionModel(
                "Один выбор"
        );
        entityManager.persist(type);

        QuestionModel question = new QuestionModel(
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

    @Test
    public void testSaveAndFindAnswerVariantModel() {
        TestModel test = new TestModel(
                "Тест по биологии",
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