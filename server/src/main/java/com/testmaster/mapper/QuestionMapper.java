package com.testmaster.mapper;

import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import com.testmaster.model.Test.Test;
import com.testmasterapi.domain.question.data.QuestionAnswerData;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public class QuestionMapper {
    @Autowired
    private AnswerTemplateMapper answerTemplateMapper;

    public QuestionData toData(Question question) {
        var questionData = new QuestionData();
        fillQuestion(questionData, question);

        return questionData;
    }

    public QuestionWithTemplatesData toDataWithTemplate(Question question, List<AnswerTemplate> answerTemplates) {
        var data = new QuestionWithTemplatesData();
        fillQuestion(data, question);

        var answerTemplateData = answerTemplates.stream()
                .map(answerTemplateMapper::toData)
                .toList();
        data.setAnswerTemplates(answerTemplateData);

        return data;
    }

    public Question toEntity(QuestionCreateRequest request, Test test) {
        var entity = new Question();
        LocalDateTime now = LocalDateTime.now();

        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setCreatedAt(now);
        entity.setTest(test);
        entity.setType(request.getType());

        return entity;
    }

    public QuestionAnswerData toAnswer(Question question) {
        var data = new QuestionAnswerData();
        data.setDescription(question.getDescription());
        data.setSoftDeleted(question.isSoftDeleted());
        data.setId(question.getId());
        data.setTitle(question.getTitle());

        return data;
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
