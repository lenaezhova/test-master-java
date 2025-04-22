package com.testmaster.mapper;

import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public class AnswerTemplateMapper {

    public AnswerTemplateData toPrivate(AnswerTemplate answerTemplate) {
        var data = new AnswerTemplateData();
        fillAnswerTemplate(data, answerTemplate);
        data.setCorrect(answerTemplate.getIsCorrect());
        data.setCountPoints(answerTemplate.getCountPoints());

        return data;
    }

    public AnswerTemplateData toPublic(AnswerTemplate answerTemplate) {
        var data = new AnswerTemplateData();
        fillAnswerTemplate(data, answerTemplate);

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

    private void fillAnswerTemplate(AnswerTemplateData data, AnswerTemplate answerTemplate) {
        data.setId(answerTemplate.getId());
        data.setDescription(answerTemplate.getDescription());
    }
}
