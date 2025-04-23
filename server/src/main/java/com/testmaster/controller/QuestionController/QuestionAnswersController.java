package com.testmaster.controller.QuestionController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.QuestionService.QuestionAnswerService.QuestionAnswerService;
import com.testmasterapi.api.QuestionApi.QuestionAnswersApi;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.test.TestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(QuestionAnswersApi.PATH)
public class QuestionAnswersController implements QuestionAnswersApi {
    private final QuestionAnswerService questionAnswerService;

    @Override
    public List<AnswerData> allAnswers(Long questionId) {
        return questionAnswerService.getAllAnswers(questionId);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "questionId", status = TestStatus.OPENED)
    public void deleteAllAnswers(Long questionId) {
        questionAnswerService.deleteAllAnswers(questionId);
    }
}

