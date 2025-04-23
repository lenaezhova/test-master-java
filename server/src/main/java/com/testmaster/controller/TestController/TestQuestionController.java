package com.testmaster.controller.TestController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.TestService.TestQuestionService.TestQuestionService;
import com.testmaster.service.TestService.TestService;
import com.testmasterapi.api.TestApi.TestApi;
import com.testmasterapi.api.TestApi.TestQuestionApi;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.test.TestStatus;
import com.testmasterapi.domain.test.data.TestData;
import com.testmasterapi.domain.test.request.TestCreateRequest;
import com.testmasterapi.domain.test.request.TestUpdateRequest;
import com.testmasterapi.domain.test.request.TestUpdateStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<QuestionData> allQuestions(Long id) {
        return testQuestionService.getAllQuestions(id);
    }

    @Override
    @CheckTest(testId = "id", checkOwner = true, status = TestStatus.CLOSED)
    public ResponseEntity<Void> createQuestion(Long id, QuestionCreateRequest request) {
        var question = testQuestionService.createQuestion(id, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @CheckTest(testId = "id", checkOwner = true, status = TestStatus.CLOSED)
    public void deleteAllQuestions(Long id) {
        testQuestionService.deleteAllQuestions(id);
    }
}
