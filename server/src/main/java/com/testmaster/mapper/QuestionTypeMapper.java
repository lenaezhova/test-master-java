package com.testmaster.mapper;

import com.testmaster.model.QuestionType;
import com.testmasterapi.domain.question.data.QuestionTypeData;
import com.testmasterapi.domain.question.request.QuestionTypeCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class QuestionTypeMapper {
    public QuestionTypeData toQuestionTypeData(QuestionType questionType) {
        var data = new QuestionTypeData();
        fillComponent(data, questionType);
        return data;
    }

    public QuestionType toEntity(QuestionTypeCreateRequest request) {
        var entity = new QuestionType();
        entity.setTitle(request.getTitle());
        entity.setTypes(request.getTypes());
        return entity;
    }

    private void fillComponent(QuestionTypeData data, QuestionType entity) {
        data.setId(entity.getId());
        data.setTitle(entity.getTitle());
        data.setTypes(entity.getTypes());
    }
}
