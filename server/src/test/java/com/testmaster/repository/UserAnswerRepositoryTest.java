package com.testmaster.repository;

import com.testmaster.model.User.UserAnswer;
import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.user.UserRoles;
import com.testmaster.model.*;
        import com.testmaster.model.Test;
import com.testmasterapi.domain.test.TestStatus;
import com.testmaster.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserAnswerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @org.junit.jupiter.api.Test
    public void testSaveUserAnswerModel() {
        User user = new User(
                false,
                "Иван Иванов",
                "ivan@example.com",
                "password123",
                "activation-code",
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

        QuestionType type = new QuestionType();
        type.setTitle("Открытый вопрос");
        type.setTypes(Set.of(QuestionTypes.TEXT));

        entityManager.persist(type);

        Question question = new Question();
        question.setSavedType(QuestionTypes.TEXT);
        question.setTest(test);
        question.setType(type);
        question.setCreatedAt(LocalDateTime.now());

        entityManager.persist(question);

        UserAnswer answer = new UserAnswer(
                question,
                test,
                user,
                true,
                1,
                LocalDateTime.now()
        );
        UserAnswer saved = userAnswerRepository.save(answer);

        assertNotNull(saved.getId());
        assertTrue(saved.getIsCorrect());
        assertEquals(1, saved.getCountPoints());
    }

    @org.junit.jupiter.api.Test
    public void testSaveAndFindUserAnswerModel() {
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
        test.setDescription("Описание теста");
        entityManager.persist(test);

        QuestionType type = new QuestionType();
        type.setTitle("Открытый вопрос");
        type.setTypes(Set.of(QuestionTypes.TEXT));

        entityManager.persist(type);

        Question question = new Question();
        question.setSavedType(QuestionTypes.TEXT);
        question.setTest(test);
        question.setType(type);
        question.setCreatedAt(LocalDateTime.now());

        entityManager.persist(question);

        UserAnswer answer = new UserAnswer(
                question,
                test,
                user,
                false,
                0,
                LocalDateTime.now()
        );

        entityManager.persist(answer);

        Optional<UserAnswer> foundOpt = userAnswerRepository.findById(answer.getId());

        assertTrue(foundOpt.isPresent());

        UserAnswer found = foundOpt.get();
        assertFalse(found.getIsCorrect());
        assertEquals(0, found.getCountPoints());
        assertEquals("Пётр Петров", found.getUser().getName());
        assertEquals("Тест по истории", found.getTest().getTitle());
    }
}