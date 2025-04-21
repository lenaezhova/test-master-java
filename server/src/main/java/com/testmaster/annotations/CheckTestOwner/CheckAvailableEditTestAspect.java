package com.testmaster.annotations.CheckTestOwner;

import com.testmaster.exeption.ClientException;
import com.testmaster.model.Question;
import com.testmaster.model.Test;
import com.testmaster.model.User;
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
public class CheckAvailableEditTestAspect {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    @Around("@annotation(checkAvailableEditTest)")
    public Object checkAvailableEditTest(ProceedingJoinPoint joinPoint, CheckAvailableEditTest checkAvailableEditTest) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        String paramsTestId = checkAvailableEditTest.testId();
        String paramsQuestionId = checkAvailableEditTest.questionId();
        boolean checkTestIsOpen = checkAvailableEditTest.checkTestIsOpen();

        Test test = null;

        if (!paramsTestId.isEmpty()) {
            var testId = getParamByName(paramNames, args, paramsTestId);
            test = this.getTest(testId);
        } else if (!paramsQuestionId.isEmpty()) {
            var questionId = getParamByName(paramNames, args, paramsQuestionId);
            var question = this.getQuestion(questionId);
            test = question.getTest();
        }

        if (test == null) {
            throw new IllegalArgumentException("Не удалось определить testId (questionId) из аннотации");
        }

        checkTestOwner(test.getOwner());
        if (checkTestIsOpen) {
            checkTestStatus(test, TestStatus.OPENED);
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
        if (test.getStatus().equals(status)) {
            throw new ClientException("Для редактирования теста необходимо его закрыть", HttpStatus.CONFLICT.value());
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
        return questionRepository.findQuestionById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Вопрос не найден"));
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