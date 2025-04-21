package com.testmaster.mapper;

import com.testmaster.model.AnswerVariant;
import com.testmaster.model.Question;
import com.testmasterapi.domain.answerVariant.data.AnswerVariantPrivateData;
import com.testmasterapi.domain.answerVariant.data.AnswerVariantPublicData;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantCreateRequest;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class AnswerVariantMapper {
    public AnswerVariantPublicData toPublic(AnswerVariant answerVariant) {
        var data = new AnswerVariantPublicData();
        fillComponent(data, answerVariant);
        return data;
    }

    public AnswerVariantPrivateData toPrivate(AnswerVariant answerVariant) {
        var data = new AnswerVariantPrivateData();
        fillComponent(data, answerVariant);
        data.setIsCorrect(answerVariant.getIsCorrect());
        data.setCountPoints(answerVariant.getCountPoints());
        return data;
    }

    public AnswerVariant toEntity(AnswerVariantCreateRequest request, Question question) {
        var entity = new AnswerVariant();

        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setCountPoints(request.countPoints());
        entity.setIsCorrect(request.isCorrect());
        entity.setQuestion(question);

        return entity;
    }

    private void fillComponent(AnswerVariantPublicData data, AnswerVariant answerVariant) {
        data.setTitle(answerVariant.getTitle());
        data.setDescription(answerVariant.getDescription());
        data.setId(answerVariant.getId());
    }
}
