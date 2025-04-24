package com.testmaster.mapper;

import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateResultData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateQuestionUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import org.mapstruct.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;


@Mapper(componentModel = "spring")
public class AnswerTemplateMapper {

    public AnswerTemplateData toData(AnswerTemplate answerTemplate) {
        var currentUser = this.getCurrentUser();
        var isOwnerTest = Objects.equals(currentUser.getId(), answerTemplate.getQuestion().getTest().getOwner().getId());
        return isOwnerTest
                ? this.toPrivate(answerTemplate)
                : this.toPublic(answerTemplate);
    }

    public AnswerTemplateResultData toResult(AnswerTemplate answerTemplate) {
        var data = new AnswerTemplateResultData();
        data.setId(answerTemplate.getId());
        data.setDescription(answerTemplate.getDescription());
        return data;

    }

    public AnswerTemplate toEntity(AnswerTemplateCreateRequest request, Question question) {
        var entity = new AnswerTemplate();

        entity.setText(request.text());
        entity.setQuestion(question);
        entity.setCountPoints(request.countPoints());
        entity.setIsCorrect(request.isCorrect());
        entity.setDescription(request.description());

        return entity;
    }

    public AnswerTemplate toEntityFromUpdateQuestion(AnswerTemplateQuestionUpdateRequest request, Question question) {
        var entity = new AnswerTemplate();

        entity.setText(request.getText());
        entity.setQuestion(question);
        entity.setCountPoints(request.getCountPoints());
        entity.setIsCorrect(request.getIsCorrect());
        entity.setDescription(request.getDescription());

        return entity;
    }

    private AnswerTemplateData toPrivate(AnswerTemplate answerTemplate) {
        var data = new AnswerTemplateData();
        fillAnswerTemplate(data, answerTemplate);
        data.setCorrect(answerTemplate.getIsCorrect());
        data.setCountPoints(answerTemplate.getCountPoints());

        return data;
    }

    private AnswerTemplateData toPublic(AnswerTemplate answerTemplate) {
        var data = new AnswerTemplateData();
        fillAnswerTemplate(data, answerTemplate);

        return data;
    }

    private void fillAnswerTemplate(AnswerTemplateData data, AnswerTemplate answerTemplate) {
        data.setId(answerTemplate.getId());
        data.setDescription(answerTemplate.getDescription());
    }

    private CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
