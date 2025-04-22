
package com.testmaster.controller;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.AnswerTemplateService.AnswerTemplateService;
import com.testmasterapi.api.AnswerTemplateApi;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
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
@RequestMapping(AnswerTemplateApi.PATH)
public class AnswerTemplateController implements AnswerTemplateApi {
    private final AnswerTemplateService answerTemplateService;

    @Override
    public List<AnswerTemplateData> allByQuestionId(Long questionId) {
        return answerTemplateService.getAllByQuestionId(questionId);
    }

    @Override
    @CheckTest(questionId = "questionId", checkOwner = true, status = TestStatus.CLOSED)
    public ResponseEntity<Void> create(Long questionId, AnswerTemplateCreateRequest request) {
        answerTemplateService.create(questionId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(answerTemplateId = "id", checkOwner = true, status = TestStatus.CLOSED)
    public void update(Long id, AnswerTemplateUpdateRequest updateRequest) {
        answerTemplateService.update(id, updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(answerTemplateId = "id", checkOwner = true, status = TestStatus.CLOSED)
    public void delete(Long id) {
        answerTemplateService.delete(id);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "questionId", checkOwner = true, status = TestStatus.CLOSED)
    public void deleteAllByQuestionId(Long questionId) {
        answerTemplateService.deleteAllByQuestionId(questionId);
    }
}
