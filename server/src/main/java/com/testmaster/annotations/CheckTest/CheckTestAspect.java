package com.testmaster.annotations.CheckTest;

import com.testmaster.exeption.ClientException;
import com.testmaster.model.*;
import com.testmaster.model.Test.Test;
import com.testmaster.model.User.User;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.user.CustomUserDetails;
import com.testmasterapi.domain.user.UserRoles;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class CheckTestAspect {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerTemplateRepository answerTemplateRepository;
    private final AnswerRepository answerRepository;

    @Around("@annotation(checkTest)")
    public Object checkTest(ProceedingJoinPoint joinPoint, CheckTest checkTest) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        String paramsTestId = checkTest.testId();
        String paramsQuestionId = checkTest.questionId();
        String paramsAnswerTemplateId = checkTest.answerTemplateId();
        String paramsAnswerId = checkTest.answerId();
        boolean checkOwner = checkTest.checkOwner();
        TestStatus status = checkTest.status();

        Test test = null;

        if (!paramsTestId.isEmpty()) {
            var testId = getParamByName(paramNames, args, paramsTestId);
            test = this.getTest(testId);
        } else if (!paramsQuestionId.isEmpty()) {
            var questionId = getParamByName(paramNames, args, paramsQuestionId);
            var question = this.getQuestion(questionId);
            test = question.getTest();
        } else if (!paramsAnswerTemplateId.isEmpty()) {
            var answerTemplateId = getParamByName(paramNames, args, paramsAnswerTemplateId);
            var answerTemplate = this.getAnswerTemplate(answerTemplateId);
            var question = answerTemplate.getQuestion();
            test = question.getTest();
        } else if (!paramsAnswerId.isEmpty()) {
            var answerId = getParamByName(paramNames, args, paramsAnswerId);
            var answer = this.getAnswer(answerId);
            var question = answer.getQuestion();
            test = question.getTest();
        }

        if (test == null) {
            throw new IllegalArgumentException("Не удалось определить testId || questionId || answerTemplateId из аннотации");
        }

        if (checkOwner) {
            checkTestOwner(test.getOwner());
        }

        if (status != null) {
            checkTestStatus(test, status);
        }

        return joinPoint.proceed();
    }

    private void checkTestOwner(User user) {
        var currentUser = this.getCurrentUser();
        Long ownerId = user.getId();

        if (!ownerId.equals(currentUser.getId()) && !currentUser.getRoles().contains(UserRoles.ADMIN)) {
            throw new IllegalArgumentException("Вы не являетесь владельцем теста");
        }
    }

    private void checkTestStatus(Test test, TestStatus status) {
        var testStatus = test.getStatus();
        if (status == TestStatus.CLOSED && !testStatus.equals(status)) {
            throw new ClientException("Тест открыт для прохождения", HttpStatus.CONFLICT.value());
        }

        if (status == TestStatus.OPENED && !testStatus.equals(status)) {
            throw new ClientException("Тест закрыт для прохождения", HttpStatus.CONFLICT.value());
        }
    }

    private Test getTest(Long testId) {
        if (testId == null) {
            throw new IllegalArgumentException("test id не передан");
        }
        return testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден"));
    }

    private Question getQuestion(Long questionId) {
        if (questionId == null) {
            throw new IllegalArgumentException("question id не передан");
        }
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Вопрос не найден"));
    }

    private AnswerTemplate getAnswerTemplate(Long answerTemplateId) {
        if (answerTemplateId == null) {
            throw new IllegalArgumentException("answer template id не передан");
        }
        return answerTemplateRepository.findById(answerTemplateId)
                .orElseThrow(() -> new IllegalArgumentException("Шаблон ответа не найден"));
    }

    private Answer getAnswer(Long answerId) {
        if (answerId == null) {
            throw new IllegalArgumentException("answer id не передан");
        }
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("Ответ не найден"));
    }

    private CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Long getParamByName(String[] paramNames, Object[] args, String name) {
        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equals(name)) {
                return (Long) args[i];
            }
        }
        return null;
    }
}