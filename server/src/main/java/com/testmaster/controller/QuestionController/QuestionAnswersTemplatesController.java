package com.testmaster.controller.QuestionController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.QuestionService.QuestionAnswerTemplate.QuestionAnswerTemplatesService;
import com.testmasterapi.api.QuestionApi.QuestionAnswerTemplatesApi;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
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
    public QuestionWithTemplatesData oneWithTemplates(Long questionId) {
        return questionAnswerTemplatesService.getOneWithTemplates(questionId);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "questionId", checkOwner = true, status = TestStatus.CLOSED)
    public void updateAnswerTemplate(Long questionId, Long answerTemplateId, AnswerTemplateUpdateRequest updateRequest) {
        questionAnswerTemplatesService.updateAnswerTemplate(questionId, answerTemplateId, updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "questionId", checkOwner = true, status = TestStatus.CLOSED)
    public void deleteAnswerTemplate(Long questionId, Long answerTemplateId) {
        questionAnswerTemplatesService.deleteAnswerTemplate(questionId, answerTemplateId);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "questionId", checkOwner = true, status = TestStatus.CLOSED)
    public void updateWithTemplates(Long questionId, QuestionUpdateWithAnswersTemplatesRequest updateRequest) {
        questionAnswerTemplatesService.updateWithTemplates(questionId, updateRequest);
    }
}

