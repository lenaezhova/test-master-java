package com.testmaster.repository;

import com.testmaster.model.TypeQuestion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TypeQuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TypeQuestionRepository typeQuestionRepository;

    @Test
    public void testSaveTypeQuestionModel() {
        TypeQuestion type = new TypeQuestion(
                "Открытый вопрос"
        );

        TypeQuestion saved = typeQuestionRepository.save(type);

        assertNotNull(saved.getId());
        assertEquals("Открытый вопрос", saved.getTitle());
    }

    @Test
    public void testFindById() {
        TypeQuestion type = new TypeQuestion(
                "Множественный выбор"
        );

        entityManager.persist(type);

        Optional<TypeQuestion> foundOpt = typeQuestionRepository.findById(type.getId());

        assertTrue(foundOpt.isPresent());
        assertEquals("Множественный выбор", foundOpt.get().getTitle());
    }

}