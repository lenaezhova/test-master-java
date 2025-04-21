package com.testmaster.controller;

import com.testmaster.annotations.CheckTestOwner.CheckAvailableEditTest;
import com.testmaster.service.QuestionService.QuestionService;
import com.testmasterapi.api.QuestionApi;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(QuestionApi.PATH)
public class QuestionController implements QuestionApi {
    private final QuestionService questionService;

    @Override
    public List<QuestionData> all() {
        return questionService.getAll();
    }

    @Override
    public List<QuestionData> allQuestionsByTestId(Long testId) {
        return questionService.getAllQuestionsByTestId(testId);
    }

    @Override
    public QuestionData one(Long id) {
        return questionService.getOne(id);
    }

    @Override
    @CheckAvailableEditTest(testId = "testId")
    public ResponseEntity<Void> create(Long testId, QuestionCreateRequest request) {
        questionService.create(testId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckAvailableEditTest(questionId = "id")
    public void update(Long id, QuestionUpdateRequest updateRequest) {
        questionService.update(id, updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckAvailableEditTest(questionId = "id")
    public void delete(Long id) {
        var data = new QuestionUpdateRequest();
        data.setSoftDeleted(true);
        questionService.update(id, data);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckAvailableEditTest(testId = "testId")
    public void deleteAllQuestion(Long testId) {
        questionService.deleteAllQuestion(testId);
    }
}
