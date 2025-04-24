package com.testmaster.controller.QuestionController;

import com.testmaster.annotations.CheckTest.CheckTest;
import com.testmaster.service.QuestionService.QuestionService;
import com.testmasterapi.api.QuestionApi.QuestionApi;
import com.testmasterapi.domain.page.data.PageData;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;
import com.testmasterapi.domain.question.reponse.QuestionsResponse;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
import com.testmasterapi.domain.test.TestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public PageData<QuestionData> all(Boolean showDeleted, Pageable pageable) {
        return questionService.getAll(showDeleted, pageable);
    }

    @Override
    public QuestionData one(Long id) {
        return questionService.getOne(id);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "id", checkOwner = true, status = TestStatus.CLOSED)
    public void update(Long id, QuestionUpdateRequest updateRequest) {
        questionService.update(id, updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckTest(questionId = "id", checkOwner = true, status = TestStatus.CLOSED)
    public void delete(Long id) {
        var data = new QuestionUpdateRequest();
        data.setSoftDeleted(true);
        questionService.update(id, data);
    }
}
