package com.testmaster.controller.QuestionController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.QuestionService.QuestionAnswerTemplate.QuestionAnswerTemplatesService;
import com.testmaster.service.QuestionService.QuestionService;
import com.testmasterapi.api.QuestionApi.QuestionAnswerTemplatesApi;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.test.TestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(QuestionAnswerTemplatesApi.PATH)
public class QuestionAnswersTemplatesController implements QuestionAnswerTemplatesApi {
    private final QuestionAnswerTemplatesService questionAnswerTemplatesService;

    @Override
    public List<AnswerTemplateData> allAnswerTemplate(Long id) {
        return questionAnswerTemplatesService.getAllAnswerTemplate(id);
    }

    @Override
    @CheckTest(questionId = "id", checkOwner = true, status = TestStatus.CLOSED)
    public ResponseEntity<Void> createAnswerTemplate(Long questionId, AnswerTemplateCreateRequest request) {
        var answerTemplate = questionAnswerTemplatesService.createAnswerTemplate(questionId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "id", checkOwner = true, status = TestStatus.CLOSED)
    public void deleteAllAnswerTemplate(Long id) {
        questionAnswerTemplatesService.deleteAllAnswerTemplate(id);
    }
}

