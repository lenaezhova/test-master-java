package com.testmaster.repository;

import com.testmaster.model.QuestionType;
import com.testmaster.repository.QuestionTypeRepository.QuestionTypeRepository;
import com.testmasterapi.domain.question.QuestionTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TypeQuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionTypeRepository typeQuestionRepository;

    @Test
    public void testSaveTypeQuestionModel() {
        QuestionType type = new QuestionType();
        type.setTitle("Открытый вопрос");
        type.setTypes(Set.of(QuestionTypes.TEXT));

        QuestionType saved = typeQuestionRepository.save(type);

        assertNotNull(saved.getId());
        assertEquals("Открытый вопрос", saved.getTitle());
    }

    @Test
    public void testFindById() {
        QuestionType type = new QuestionType();
        type.setTitle("Множественный выбор");
        type.setTypes(Set.of(QuestionTypes.MULTIPLE));

        entityManager.persist(type);

        Optional<QuestionType> foundOpt = typeQuestionRepository.findById(type.getId());

        assertTrue(foundOpt.isPresent());
        assertEquals("Множественный выбор", foundOpt.get().getTitle());
    }

}