package com.testmaster.service.QuestionService.QuestionAnswerTemplate;

import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerTemplateMapper;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmasterapi.domain.answerTemplate.event.AnswerTemplateEvent;
import com.testmasterapi.domain.answerTemplate.event.AnswerTemplateEventsType;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateQuestionUpdateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
import com.testmasterapi.domain.question.QuestionTypes;
import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;
import com.testmasterapi.domain.question.event.QuestionEvent;
import com.testmasterapi.domain.question.event.QuestionEventsType;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultQuestionAnswerTemplateService implements QuestionAnswerTemplatesService {
    private final QuestionMapper questionMapper;
    private final AnswerTemplateMapper answerTemplateMapper;

    private final QuestionRepository questionRepository;
    private final AnswerTemplateRepository answerTemplateRepository;

    private final String notFoundAnswerTemplateMessage = "Шаблон ответа не найден";
    private final String notFoundQuestionMessage = "Вопрос не найден";
    private final ApplicationEventPublisher applicationEventPublisher;

    public QuestionWithTemplatesData getOneWithTemplates(Long questionId) {
        var question = this.getQuestion(questionId);
        var answerTemplates = answerTemplateRepository.findAllByQuestionId(questionId);

        return questionMapper.toDataWithTemplate(question, answerTemplates);
    }

    @Override
    @Transactional
    public void updateAnswerTemplate(Long questionId, Long answerTemplateId, AnswerTemplateUpdateRequest request) {
        var answerTemplate =  this.getAnswerTemplate(questionId);
        applicationEventPublisher.publishEvent(new AnswerTemplateEvent(answerTemplate, AnswerTemplateEventsType.UPDATE));

        int updated = answerTemplateRepository.update(answerTemplateId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }

    @Override
    @Transactional
    public void deleteAnswerTemplate(Long questionId, Long answerTemplateId) {
        var answerTemplate =  this.getAnswerTemplate(questionId);
        applicationEventPublisher.publishEvent(new AnswerTemplateEvent(answerTemplate, AnswerTemplateEventsType.DELETE));

        int deleted = answerTemplateRepository.delete(answerTemplateId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }

    @Override
    @Transactional
    public void updateWithTemplates(Long questionId, @NotNull QuestionUpdateWithAnswersTemplatesRequest request) {
        var question = this.getQuestion(questionId);

        if (question.getType() == QuestionTypes.TEXT && request.getAnswerTemplates().size() > 1) {
            throw new ClientException("В текстовом вопросе не может быть больше 1 варианта ответа", HttpStatus.CONFLICT.value());
        }

        applicationEventPublisher.publishEvent(new QuestionEvent(question, QuestionEventsType.UPDATE));

        int updated = questionRepository.update(questionId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundQuestionMessage);
        }

        List<AnswerTemplateQuestionUpdateRequest> newTemplates = new ArrayList<>();

        for (var template : request.getAnswerTemplates()) {
            if (template.getId() != null) {
                answerTemplateRepository.update(template.getId(), template);
            } else {
                newTemplates.add(template);
            }
        }

        if (!newTemplates.isEmpty()) {
            List<AnswerTemplate> answerTemplates = newTemplates
                    .stream()
                    .map(template -> answerTemplateMapper.toEntityFromUpdateQuestion(template, question))
                    .collect(Collectors.toList());

            answerTemplateRepository.saveAll(answerTemplates);
        }
    }

    private AnswerTemplate getAnswerTemplate(Long answerTemplateId) {
        return answerTemplateRepository.findById(answerTemplateId)
                .orElseThrow(() -> new IllegalArgumentException("Шаблон ответа не найден"));
    }

    private Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException(notFoundQuestionMessage));
    }
}
