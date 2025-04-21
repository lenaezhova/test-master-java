package com.testmaster.mapper;

import com.testmasterapi.domain.question.AnswerTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class AnswerTemplateMapper {
    public AnswerTemplate toPublic(AnswerTemplate answerTemplate) {
        var publicData = new AnswerTemplate();

        publicData.setDescription(answerTemplate.getDescription());
        publicData.setText(answerTemplate.getText());

        return publicData;
    }
}
