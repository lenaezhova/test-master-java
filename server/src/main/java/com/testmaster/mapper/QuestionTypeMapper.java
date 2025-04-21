package com.testmaster.mapper;

import com.testmaster.model.QuestionType;
import com.testmasterapi.domain.question.data.QuestionTypeData;
import com.testmasterapi.domain.question.request.QuestionTypeCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class QuestionTypeMapper {
    public QuestionTypeData toQuestionTypeData(QuestionType questionType) {
        var data = new QuestionTypeData();
        data.setId(questionType.getId());
        data.setTitle(questionType.getTitle());
        data.setType(questionType.getType());
        return data;
    }

    public QuestionType toEntity(QuestionTypeCreateRequest request) {
        var entity = new QuestionType();
        entity.setTitle(request.getTitle());
        entity.setType(request.getType());
        return entity;
    }
}
