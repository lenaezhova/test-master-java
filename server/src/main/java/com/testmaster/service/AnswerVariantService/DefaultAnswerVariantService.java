package com.testmaster.service.QuestionService;

import com.testmaster.exeption.ClientException;
import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.AnswerVariantMapper;
import com.testmaster.model.AnswerVariant;
import com.testmaster.model.Question;
import com.testmaster.model.Test;
import com.testmaster.model.User.User;
import com.testmaster.repository.AnswerVariantRepository.AnswerVariantRepository;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.service.AnswerVariantService.AnswerVariantService;
import com.testmasterapi.domain.answerVariant.data.AnswerVariantPrivateData;
import com.testmasterapi.domain.answerVariant.data.AnswerVariantPublicData;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantCreateRequest;
import com.testmasterapi.domain.answerVariant.request.AnswerVariantUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import com.testmasterapi.domain.user.UserRoles;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultAnswerVariantService implements AnswerVariantService {
    private final AnswerVariantMapper answerVariantMapper;

    private final AnswerVariantRepository answerVariantRepository;
    private final QuestionRepository questionRepository;

    public List<AnswerVariantPrivateData> getAllQuestionAnswerVariants(Long questionId) {
        Question question = this.getQuestion(questionId);
        boolean isOwnerTest = this.checkIsTestOwnerOrAdmin(question);

        return answerVariantRepository
                .findAllByQuestionId(questionId)
                .stream()
                .map(variant -> (AnswerVariantPrivateData) (
                                isOwnerTest
                                        ? answerVariantMapper.toPrivate(variant)
                                        : answerVariantMapper.toPublic(variant)
                        )
                )
                .toList();
    }

    @Override
    public List<AnswerVariantPrivateData> getAll() {
        return answerVariantRepository
                .findAll()
                .stream()
                .map(answerVariantMapper::toPrivate)
                .toList();
    }

    @Override
    public AnswerVariantPrivateData getOne(Long id) {
        var data = this.getAnswerVariant(id);

        return answerVariantMapper.toPrivate(data);
    }

    @NotNull
    @Transactional
    @Override
    public AnswerVariantPublicData create(@NotNull AnswerVariantCreateRequest request) {
        var question = this.getQuestion(request.questionId());
        var answerVariant = answerVariantMapper.toEntity(request, question);

        answerVariantRepository.save(answerVariant);
        return answerVariantMapper.toPublic(answerVariant);
    }

    @Override
    @Transactional
    public void update(Long id, AnswerVariantUpdateRequest request) {
        var answerVariant = this.getAnswerVariant(id);
        checkIsOwnerTest(answerVariant);

        answerVariantRepository.update(id, request);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var answerVariant = this.getAnswerVariant(id);
        checkIsOwnerTest(answerVariant);

        answerVariantRepository.delete(id);
    }

    private AnswerVariant getAnswerVariant(Long id) {
        return answerVariantRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Вариант ответа не найден"));
    }

    private Question getQuestion(Long questionId) {
        return questionRepository
                .findById(questionId)
                .orElseThrow(() -> new NotFoundException("Вопрос не найден"));
    }

    private void checkIsOwnerTest(AnswerVariant answerVariant) {
        boolean isOwnerTest = this.checkIsTestOwnerOrAdmin(answerVariant.getQuestion());

        if (!isOwnerTest) {
            throw new ClientException("Варинат ответа может редактировать только создатель теста", HttpStatus.CONFLICT.value());
        }
    }

    private CustomUserDetails getUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Boolean checkIsTestOwnerOrAdmin(Question question) {
        Test test = question.getTest();

        if (test.getDeleted()) {
            throw new NotFoundException("Тест был удален");
        }

        User owner = test.getOwner();

        if (owner.getDeleted()) {
            throw new NotFoundException("Пользователь был удален");
        }

        var currentUser = this.getUser();

        return Objects.equals(owner.getId(), currentUser.getId()) ||
                currentUser.getRoles().contains(UserRoles.ADMIN);
    }
}
