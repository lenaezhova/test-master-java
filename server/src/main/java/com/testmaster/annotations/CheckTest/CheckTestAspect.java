package com.testmaster.annotations.CheckTest;

import com.testmaster.exeption.ClientException;
import com.testmaster.model.*;
import com.testmaster.model.Test.Test;
import com.testmaster.model.User.User;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmaster.repository.TestSessionRepository.TestSessionRepository;
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
    private final TestSessionRepository testSessionRepository;

    @Around("@annotation(checkTest)")
    public Object checkTest(ProceedingJoinPoint joinPoint, CheckTest checkTest) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        String paramsTestId = checkTest.testId();
        String paramsQuestionId = checkTest.questionId();
        String paramsAnswerTemplateId = checkTest.answerTemplateId();
        String paramsAnswerId = checkTest.answerId();
        String paramsTestSessionId = checkTest.testSessionId();
        boolean skipCheckStatusForOwner = checkTest.skipCheckStatusForOwner();
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
        } else if (!paramsTestSessionId.isEmpty()) {
            var testSessionId = getParamByName(paramNames, args, paramsTestSessionId);
            var testSession = this.getTestSession(testSessionId);
            test = testSession.getTest();
        }

        if (test == null) {
            throw new IllegalArgumentException("Не удалось определить id из аннотации");
        }

        var isOwner = checkTestOwner(test, checkOwner);

        if (status != TestStatus.UNSPECIFIED && (!skipCheckStatusForOwner || !isOwner)) {
            checkTestStatus(test, status);
        }

        return joinPoint.proceed();
    }

    private boolean checkTestOwner(Test test, boolean throwError) {
        var currentUserDetails = this.getCurrentUserDetails();
        Long ownerId = test.getOwner().getId();

        if (!ownerId.equals(currentUserDetails.getId()) && !currentUserDetails.getRoles().contains(UserRoles.ADMIN)) {
            if (throwError) {
                throw new IllegalArgumentException("Вы не являетесь владельцем теста");
            }
            return false;
        }

        return true;
    }

    private void checkTestStatus(Test test, TestStatus expectedStatus) {
        TestStatus actualStatus = test.getStatus();

        if (!expectedStatus.equals(actualStatus)) {
            String message = switch (expectedStatus) {
                case CLOSED -> "Тест открыт для прохождения";
                case OPENED -> "Тест закрыт для прохождения";
                default -> "Некорректный статус теста";
            };
            throw new ClientException(message, HttpStatus.CONFLICT.value());
        }
    }

    private Test getTest(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден"));
    }

    private Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Вопрос не найден"));
    }

    private TestSession getTestSession(Long testSessionId) {
        return testSessionRepository.findById(testSessionId)
                .orElseThrow(() -> new IllegalArgumentException("Сессия теста не найдена"));
    }

    private AnswerTemplate getAnswerTemplate(Long answerTemplateId) {
        return answerTemplateRepository.findById(answerTemplateId)
                .orElseThrow(() -> new IllegalArgumentException("Шаблон ответа не найден"));
    }

    private Answer getAnswer(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("Ответ не найден"));
    }

    private CustomUserDetails getCurrentUserDetails() {
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