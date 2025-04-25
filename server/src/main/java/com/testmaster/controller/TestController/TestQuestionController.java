package com.testmaster.controller.TestController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.TestService.TestQuestionService.TestQuestionService;
import com.testmasterapi.api.TestApi.TestQuestionApi;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;
import com.testmasterapi.domain.question.reponse.QuestionsResponse;
import com.testmasterapi.domain.question.reponse.QuestionsWithTemplatesResponse;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionCreateWithAnswersTemplatesRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(TestQuestionApi.PATH)
public class TestQuestionController implements TestQuestionApi {
    private final TestQuestionService testQuestionService;

    @Override
    public QuestionsResponse allQuestions(Long testId, Boolean showQuestionSoftDeleted) {
        return new QuestionsResponse(testQuestionService.getAllQuestions(testId, showQuestionSoftDeleted));
    }

    @Override
    public QuestionsWithTemplatesResponse allQuestionsAnswerTemplates(Long testId, Boolean showQuestionSoftDeleted) {
        return new QuestionsWithTemplatesResponse(testQuestionService.getAllQuestionsWithTemplates(testId, showQuestionSoftDeleted));
    }

    @Override
    @CheckTest(testId = "testId", checkOwner = true, status = TestStatus.CLOSED)
    public ResponseEntity<Void> createQuestion(Long testId, QuestionCreateRequest request) {
        var question = testQuestionService.createQuestion(testId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @CheckTest(testId = "testId", checkOwner = true, status = TestStatus.CLOSED)
    public ResponseEntity<Void> createQuestionWithTemplates(Long testId, QuestionCreateWithAnswersTemplatesRequest request) {
        var question = testQuestionService.createQuestionWithTemplates(testId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @CheckTest(testId = "testId", checkOwner = true, status = TestStatus.CLOSED)
    public void deleteAllQuestions(Long testId) {
        testQuestionService.deleteAllQuestions(testId);
    }
}
