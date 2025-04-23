
package com.testmaster.controller;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.AnswerService.AnswerService;
import com.testmasterapi.api.AnswerApi;
import com.testmasterapi.domain.answer.request.AnswerUpdateRequest;
import com.testmasterapi.domain.test.TestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}
