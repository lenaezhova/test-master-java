package com.testmaster.service.QuestionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerMapper;
import com.testmaster.mapper.AnswerTemplateMapper;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.model.AnswerTemplate;
import com.testmaster.model.Question;
import com.testmaster.repository.AnswerRepository.AnswerRepository;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmasterapi.domain.answer.data.AnswerData;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultQuestionService implements QuestionService {
    private final QuestionMapper questionMapper;
    private final AnswerTemplateMapper answerTemplateMapper;
    private final AnswerMapper answerMapper;

    private final QuestionRepository questionRepository;
    private final AnswerTemplateRepository answerTemplateRepository;
    private final AnswerRepository answerRepository;

    private final String notFoundQuestionMessage = "Вопрос не найден";

    @Override
    public List<QuestionData> getAll() {
        return questionRepository.findAll()
                .stream()
                .map(questionMapper::toData)
                .toList();
    }

    @Override
    public List<AnswerTemplateData> getAllAnswerTemplate(Long questionId) {
        return answerTemplateRepository.findAllByQuestionId(questionId)
                .stream()
                .map(answerTemplateMapper::toData)
                .toList();
    }

    @Override
    public List<AnswerData> getAllAnswers(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId)
                .stream()
                .map(answerMapper::toData)
                .toList();
    }

    @NotNull
    @Transactional
    @Override
    public AnswerTemplateData createAnswerTemplate(Long questionId, @NotNull AnswerTemplateCreateRequest request) {
        var question = this.getQuestion(questionId);

        AnswerTemplate entity = answerTemplateMapper.toEntity(request, question);

        answerTemplateRepository.save(entity);
        return answerTemplateMapper.toData(entity);
    }

    @Override
    public QuestionData getOne(Long id) {
        return questionMapper.toData(this.getQuestion(id));
    }

    @Override
    @Transactional
    public void update(Long questionId, QuestionUpdateRequest request) {
        int updated = questionRepository.update(questionId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundQuestionMessage);
        }
    }

    @Override
    @Transactional
    public void deleteAllAnswerTemplate(Long questionId) {
        int deleted = answerTemplateRepository.deleteAllByQuestionId(questionId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundQuestionMessage);
        }
    }

    @Override
    @Transactional
    public void deleteAllAnswers(Long questionId) {
        int deleted = answerRepository.deleteAllByQuestionId(questionId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundQuestionMessage);
        }
    }

    private Question getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException(notFoundQuestionMessage));
    }
}
