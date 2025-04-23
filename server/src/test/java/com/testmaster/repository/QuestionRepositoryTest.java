package com.testmaster.repository;

import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.Test.Test;
import com.testmasterapi.domain.test.TestStatus;
import com.testmaster.model.Question;
import com.testmaster.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

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
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Test test = new Test();
        test.setOwner(user);
        test.setTitle("Тест по истории");
        test.setStatus(TestStatus.CLOSED);
        test.setDescription("Описание");

        entityManager.persist(test);

        Question question = new Question();
        question.setType(QuestionTypes.TEXT);
        question.setTest(test);
        question.setCreatedAt(LocalDateTime.now());
        entityManager.persist(question);

        Question saved = questionRepository.save(question);

        assertNotNull(saved.getId());
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
                Set.of(UserRoles.USER),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        entityManager.persist(user);

        Test test = new Test();
        test.setOwner(user);
        test.setTitle("Тест по географии");
        test.setStatus(TestStatus.CLOSED);
        test.setDescription("Описание теста");

        entityManager.persist(test);

        Question question = new Question();
        question.setType(QuestionTypes.TEXT);
        question.setTest(test);
        question.setCreatedAt(LocalDateTime.now());
        entityManager.persist(question);

        Optional<Question> foundOpt = questionRepository.findById(question.getId());

        assertTrue(foundOpt.isPresent());
        Question found = foundOpt.get();

        assertEquals("Тест по географии", found.getTest().getTitle());
    }
}