package com.testmaster.mapper;

import com.testmaster.model.*;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answer.request.AnswerCreateRequest;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public class AnswerMapper {
    @Autowired
    private QuestionMapper questionMapper;

    public AnswerData toData(Answer answer) {
        var data = new AnswerData();

        data.setId(answer.getId());
        data.setQuestion(questionMapper.toAnswer(answer.getQuestion()));
        data.setText(answer.getText());
        data.setCreatedAt(answer.getCreatedAt());
        data.setUpdatedAt(answer.getUpdatedAt());

        return data;
    }

    public Answer toEntity(AnswerCreateRequest request, TestSession testSession, Question question, AnswerTemplate answerTemplate) {
        var entity = new Answer();
        LocalDateTime now = LocalDateTime.now();

        entity.setQuestion(question);
        entity.setTestSession(testSession);
        entity.setAnswerTemplate(answerTemplate);
        entity.setText(request.getText());
        entity.setCreatedAt(now);

        return entity;
    }
}
