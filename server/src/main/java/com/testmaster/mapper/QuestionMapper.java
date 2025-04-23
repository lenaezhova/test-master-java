package com.testmaster.mapper;

import com.testmaster.model.Question;
import com.testmaster.model.Test.Test;
import com.testmasterapi.domain.question.data.QuestionAnswerData;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import org.mapstruct.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Objects;

@Mapper(componentModel = "spring")
public class QuestionMapper {

    public QuestionData toData(Question question) {
        var currentUser = this.getCurrentUser();
        var isOwnerTest = Objects.equals(currentUser.getId(), question.getTest().getOwner().getId());
        return isOwnerTest
                ? this.toPrivate(question)
                : this.toPublic(question);
    }

    public Question toEntity(QuestionCreateRequest request, Test test) {
        var entity = new Question();
        LocalDateTime now = LocalDateTime.now();

        entity.setTitle(request.title());
        entity.setDescription(request.description());
//        entity.setAnswerTemplates(request.answerTemplates());
        entity.setCreatedAt(now);
        entity.setTest(test);
        entity.setType(request.type());

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

    private QuestionData toPrivate(Question question) {
        var questionData = new QuestionData();
        fillQuestion(questionData, question);
//        questionData.setAnswerTemplates(question.getAnswerTemplates());

        return questionData;
    }

    private QuestionData toPublic(Question question) {
        var questionData = new QuestionData();
//        var questionAnswerTemplates = question.getAnswerTemplates();
//
//        List<AnswerTemplate> publicAnswerTemplates =
//                questionAnswerTemplates
//                        .stream()
//                        .map(answerTemplateMapper::toPublic)
//                        .toList();

        fillQuestion(questionData, question);
//        questionData.setAnswerTemplates(publicAnswerTemplates);

        return questionData;
    }

    private void fillQuestion(QuestionData data, Question question) {
        data.setId(question.getId());
        data.setTitle(question.getTitle());
        data.setDescription(question.getDescription());
        data.setType(question.getType());
        data.setCreatedAt(question.getCreatedAt());
        data.setUpdatedAt(question.getUpdatedAt());
    }

    private CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
