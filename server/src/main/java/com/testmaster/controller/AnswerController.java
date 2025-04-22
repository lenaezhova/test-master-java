
package com.testmaster.controller;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.AnswerService.AnswerService;
import com.testmaster.service.AnswerTemplateService.AnswerTemplateService;
import com.testmasterapi.api.AnswerApi;
import com.testmasterapi.api.AnswerTemplateApi;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answer.request.AnswerCreateRequest;
import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;
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
@RequestMapping(AnswerApi.PATH)
public class AnswerController implements AnswerApi {
    private final AnswerService answerService;

    @Override
    public List<AnswerData> allByQuestionId(Long questionId) {
        return answerService.getAllByQuestionId(questionId);
    }

    @Override
    @CheckTest(questionId = "questionId", status = TestStatus.OPENED)
    public ResponseEntity<Void> create(Long testSessionId, Long questionId, Long answerTemplateId, AnswerCreateRequest request) {
        var answer = answerService.create(testSessionId, questionId, answerTemplateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(answerId = "id", status = TestStatus.OPENED)
    public void update(Long id, AnswerUpdateRequest request) {
        answerService.update(id, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(answerId = "id", status = TestStatus.OPENED)
    public void delete(Long id) {
        answerService.delete(id);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "questionId", status = TestStatus.OPENED)
    public void deleteAllByQuestionId(Long questionId) {
        answerService.deleteAllByQuestionId(questionId);
    }
}
