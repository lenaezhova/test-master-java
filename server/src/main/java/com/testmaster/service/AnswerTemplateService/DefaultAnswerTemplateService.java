package com.testmaster.service.AnswerTemplateService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerTemplateMapper;
import com.testmaster.model.AnswerTemplate;
import com.testmaster.repository.AnswerTemplateRepository.AnswerTemplateRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmasterapi.domain.answerTemplate.data.AnswerTemplateData;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateCreateRequest;
import com.testmasterapi.domain.answerTemplate.request.AnswerTemplateUpdateRequest;
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
public class DefaultAnswerTemplateService implements AnswerTemplateService {
    private final AnswerTemplateMapper answerTemplateMapper;

    private final QuestionRepository questionRepository;
    private final AnswerTemplateRepository answerTemplateRepository;

    private final String notFoundAnswerTemplateMessage = "Шаблон ответа не найден";

    @Override
    public List<AnswerTemplateData> getAllByQuestionId(Long questionId) {
        return answerTemplateRepository.findAllByQuestionId(questionId)
                .stream()
                .map(this::mapAnswerTemplateData)
                .toList();
    }

    @NotNull
    @Transactional
    @Override
    public AnswerTemplateData create(Long questionId, @NotNull AnswerTemplateCreateRequest request) {

        var question = questionRepository.findQuestionById(questionId)
                .orElseThrow(() -> new NotFoundException("Вопрос не найден"));

        AnswerTemplate entity = answerTemplateMapper.toEntity(request, question);

        answerTemplateRepository.save(entity);

        return this.mapAnswerTemplateData(entity);
    }

    @Override
    @Transactional
    public void update(Long answerTemplateId, AnswerTemplateUpdateRequest request) {
        int updated = answerTemplateRepository.update(answerTemplateId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }

    @Override
    @Transactional
    public void delete(Long answerTemplateId) {
        int deleted = answerTemplateRepository.delete(answerTemplateId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundAnswerTemplateMessage);
        }
    }

    @Override
    @Transactional
    public void deleteAllByQuestionId(Long questionId) {
        int deleted = answerTemplateRepository.deleteAllByQuestionId(questionId);
        if (deleted == 0) {
            throw new NotFoundException("Вопрос не найден");
        }
    }

    private AnswerTemplateData mapAnswerTemplateData(AnswerTemplate answerTemplate) {
        var currentUser = this.getCurrentUser();
        var isOwnerTest = Objects.equals(currentUser.getId(), answerTemplate.getQuestion().getTest().getOwner().getId());
        return isOwnerTest
                ? answerTemplateMapper.toPrivate(answerTemplate)
                : answerTemplateMapper.toPublic(answerTemplate);
    }

    private CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
