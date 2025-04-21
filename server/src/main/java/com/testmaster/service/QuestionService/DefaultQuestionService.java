package com.testmaster.service.QuestionService;

import com.testmaster.exeption.NotFoundException;
import com.testmaster.mapper.QuestionMapper;
import com.testmaster.model.Question;
import com.testmaster.repository.QuestionRepository.QuestionRepository;
import com.testmaster.repository.TestRepository.TestRepository;
import com.testmasterapi.domain.question.data.QuestionData;
import com.testmasterapi.domain.question.request.QuestionCreateRequest;
import com.testmasterapi.domain.question.request.QuestionUpdateRequest;
import com.testmasterapi.domain.user.CustomUserDetails;
import com.testmasterapi.domain.user.UserRoles;
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

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    private final String notFoundExQuestion = "Вопрос не найден";

    @Override
    public List<QuestionData> getAll() {
        return questionRepository.findAllQuestions()
                .stream()
                .map(this::mapQuestionData)
                .toList();
    }

    @Override
    public List<QuestionData> getAllQuestionsByTestId(Long testId) {
        return questionRepository.findAllQuestionsByTestId(testId)
                .stream()
                .map(this::mapQuestionData)
                .toList();
    }

    @Override
    public QuestionData getOne(Long id) {
        var data = questionRepository.findQuestionById(id)
                .orElseThrow(() -> new NotFoundException(notFoundExQuestion));

        return this.mapQuestionData(data);
    }

    @NotNull
    @Transactional
    @Override
    public QuestionData create(Long testId, @NotNull QuestionCreateRequest request) {

        var test = testRepository.findTestById(testId)
                .orElseThrow(() -> new NotFoundException("Тест не найден"));

        Question question = questionMapper.toEntity(request, test);

        questionRepository.save(question);
        return this.mapQuestionData(question);
    }

    @Override
    @Transactional
    public void update(Long questionId, QuestionUpdateRequest request) {
        int updated = questionRepository.update(questionId, request);
        if (updated == 0) {
            throw new NotFoundException(notFoundExQuestion);
        }
    }

    @Override
    @Transactional
    public void deleteAllQuestion(Long testId) {
        int deleted = questionRepository.deleteAllQuestionByTestId(testId);
        if (deleted == 0) {
            throw new NotFoundException(notFoundExQuestion);
        }
    }

    private QuestionData mapQuestionData(Question question) {
        var currentUser = this.getCurrentUser();
        var isOwnerTest = Objects.equals(currentUser.getId(), question.getTest().getOwner().getId());
        return isOwnerTest
                ? questionMapper.toPrivate(question)
                : questionMapper.toPublic(question);
    }

    private CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
