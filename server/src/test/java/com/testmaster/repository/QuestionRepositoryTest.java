package com.testmaster.repository;

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
public class QuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testSaveQuestionModel() {
        TestModel test = new TestModel(
                "Тест по истории",
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
        TestModel test = new TestModel(
                "Тест по географии",
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