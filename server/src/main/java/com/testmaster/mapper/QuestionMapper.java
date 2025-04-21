package com.testmaster.mapper;

import com.testmaster.model.Question;
import com.testmaster.model.Test;
import com.testmasterapi.domain.question.AnswerTemplate;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public class QuestionMapper {
    private AnswerTemplateMapper answerTemplateMapper;

    public QuestionData toPrivate(Question question) {
        var questionData = new QuestionData();
        fillQuestion(questionData, question);
        questionData.setAnswerTemplates(question.getAnswerTemplates());

        return questionData;
    }

    public QuestionData toPublic(Question question) {
        var questionData = new QuestionData();
        var questionAnswerTemplates = question.getAnswerTemplates();

        List<AnswerTemplate> publicAnswerTemplates =
                questionAnswerTemplates
                        .stream()
                        .map(answerTemplateMapper::toPublic)
                        .toList();

        fillQuestion(questionData, question);
        questionData.setAnswerTemplates(publicAnswerTemplates);

        return questionData;
    }

    public Question toEntity(QuestionCreateRequest request, Test test) {
        var entity = new Question();
        LocalDateTime now = LocalDateTime.now();

        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setAnswerTemplates(request.answerTemplates());
        entity.setCreatedAt(now);
        entity.setTest(test);
        entity.setType(request.type());

        return entity;
    }

    private void fillQuestion(QuestionData data, Question question) {
        data.setId(question.getId());
        data.setTitle(question.getTitle());
        data.setDescription(question.getDescription());
        data.setType(question.getType());
        data.setCreatedAt(question.getCreatedAt());
        data.setUpdatedAt(question.getUpdatedAt());

    }
}
