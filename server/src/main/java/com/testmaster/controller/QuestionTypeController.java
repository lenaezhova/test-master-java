package com.testmaster.controller;

import com.testmaster.service.QuestionTypeService.QuestionTypeService;
import com.testmasterapi.api.QuestionTypeApi;
import com.testmasterapi.domain.question.data.QuestionTypeData;
import com.testmasterapi.domain.question.request.QuestionTypeCreateRequest;
import com.testmasterapi.domain.question.request.QuestionTypeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(QuestionTypeApi.PATH)
public class QuestionTypeController implements QuestionTypeApi {
    private final QuestionTypeService questionTypeService;

    @Override
    public List<QuestionTypeData> all() {
        return questionTypeService.getAll();
    }

    @Override
    public QuestionTypeData one(Long typeId) {
        return questionTypeService.getOne(typeId);
    }

    @Override
    public ResponseEntity<Void> create(QuestionTypeCreateRequest request) {
        var data = questionTypeService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Long typeId, QuestionTypeUpdateRequest request) {
        questionTypeService.update(typeId, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long typeId) {
        questionTypeService.delete(typeId);
    }
}
