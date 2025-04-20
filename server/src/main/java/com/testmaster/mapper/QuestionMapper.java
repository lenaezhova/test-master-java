package com.testmaster.mapper;

import com.testmaster.model.Question;
import com.testmaster.model.QuestionType;
import com.testmaster.model.Test;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public class QuestionMapper {
    @Autowired
    private QuestionTypeMapper questionTypeMapper;

    public QuestionData toQuestionData(Question question) {
        var questionData = new QuestionData();

        questionData.setId(question.getId());
        questionData.setTitle(question.getTitle());
        questionData.setDescription(question.getDescription());
        questionData.setType(questionTypeMapper.toQuestionTypeData(question.getType()));
        questionData.setSavedType(question.getSavedType());
        questionData.setCreatedAt(question.getCreatedAt());
        questionData.setUpdatedAt(question.getUpdatedAt());

        return questionData;
    }

    public Question toEntity(QuestionCreateRequest request, Test test, QuestionType questionType) {
        var entity = new Question();
        LocalDateTime now = LocalDateTime.now();

        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setSavedType(request.savedType());
        entity.setCreatedAt(now);
        entity.setTest(test);
        entity.setType(questionType);

        return entity;
    }
}
