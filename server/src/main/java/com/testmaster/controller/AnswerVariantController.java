package com.testmaster.controller;

import com.testmaster.service.AnswerVariantService.AnswerVariantService;
import com.testmasterapi.api.AnswerVariantApi;
import com.testmasterapi.domain.answerVariant.data.AnswerVariantPrivateData;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantCreateRequest;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AnswerVariantApi.PATH)
public class AnswerVariantController implements AnswerVariantApi {
    private final AnswerVariantService answerVariantService;

    @Override
    public List<AnswerVariantPrivateData> allAnswerVariantsQuestion(Long questionId) {
        return answerVariantService.getAllQuestionAnswerVariants(questionId);
    }

    @Override
    public List<AnswerVariantPrivateData> all() {
        return answerVariantService.getAll();
    }

    @Override
    public ResponseEntity<Void> create(AnswerVariantCreateRequest request) {
        answerVariantService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Long id, AnswerVariantUpdateRequest request) {
        answerVariantService.update(id, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        answerVariantService.delete(id);
    }
}