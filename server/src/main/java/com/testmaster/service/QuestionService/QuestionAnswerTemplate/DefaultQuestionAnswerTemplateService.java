package com.testmaster.service.QuestionService.QuestionAnswerTemplate;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.mapper.AnswerTemplateMapper;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.service.QuestionService.QuestionService;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateQuestionUpdateRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.data.QuestionWithTemplatesData;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateWithAnswersTemplatesRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

    private final String notFoundQuestionMessage = "Вопрос не найден";

    public QuestionWithTemplatesData getOneWithTemplates(Long questionId) {
        var question = this.getQuestion(questionId);
        var answerTemplates = answerTemplateRepository.findAllByQuestionId(questionId);

        return questionMapper.toDataWithTemplate(question, answerTemplates);
    }

    @Override
    @Transactional
    public void updateWithTemplates(Long questionId, @NotNull QuestionUpdateWithAnswersTemplatesRequest request) {
        int updated = questionRepository.update(questionId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundQuestionMessage);
        }

        Question question = new Question();
        question.setId(questionId);

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

    private Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException(notFoundQuestionMessage));
    }
}
